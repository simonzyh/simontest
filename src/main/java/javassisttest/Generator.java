package javassisttest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yehua.zyh on 2016/6/17.
 */
public class Generator {
    private static final AtomicInteger classNameIndex = new AtomicInteger(1000);
    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);
    private static final String packageName = Copier.class.getPackage().getName();

    private final String SOURCE = "s";
    private final String TARGET = "t";
    private Class source;
    private Class target;
    private String[] ignoreProperties;


    /**
     * 是否为包装类
     */
    public static boolean isWrapClass(Class<?> clazz) {
        try {
            return ((Class) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * source对象类型是否是target对象类型的包装类
     */
    public static boolean isWrapClass(Class source, Class target) {
        if (!target.isPrimitive()) {
            return false;
        }
        try {
            return source.getField("TYPE").get(null) == target;
        } catch (Exception e) {
            return false;
        }
    }

    public void setSource(Class source) {
        this.source = source;
    }

    public void setTarget(Class target) {
        this.target = target;
    }

    public String[] getIgnoreProperties() {
        return ignoreProperties;
    }

    public void setIgnoreProperties(String[] ignoreProperties) {
        this.ignoreProperties = ignoreProperties;
    }

    private String generateBegin(String methodName) {
        // 生成方法签名public void copy(Object s1, Object t1) {
        String beginSource = "public void " + methodName + "(Object " + SOURCE + "1, Object " + TARGET + "1) {\n";
        // 强制转换源对象
        String convertSource = "\t" + source.getName() + " " + SOURCE + " = " + "(" + source.getName() + ")" + SOURCE + "1;\n";
        // 强制转换目标对象
        String convertTarget = "\t" + target.getName() + " " + TARGET + " = " + "(" + target.getName() + ")" + TARGET + "1;\n";
        beginSource += convertSource + convertTarget;
        return beginSource;
    }

    private String generateEnd() {
        return "}\n";
    }

    private List<String> generateBody(boolean isCheckNull) {
        List<String> propSources = Lists.newArrayList();
        PropertyDescriptor[] getters = getPropertyDescriptors(source);
        PropertyDescriptor[] setters = getPropertyDescriptors(target);

        Map<String, PropertyDescriptor> getterMap = Maps.newHashMap();
        for (PropertyDescriptor getter : getters) {
            getterMap.put(getter.getName(), getter);
        }

        for (PropertyDescriptor setter : setters) {
            PropertyDescriptor getter = getterMap.get(setter.getName());
            if (!checkCanGenSource(setter, getter)) {
                continue;
            }
            Method readMethod = getter.getReadMethod();
            Method writeMethod = setter.getWriteMethod();
            String readMethodName = readMethod.getName();
            String writerMethodName = writeMethod.getName();
            if (compatible(getter, setter)) {
                propSources.add(genPropertySource(writerMethodName, SOURCE + "." + readMethodName + "()", isCheckNull));
            } else {
                // 是否是包装类转换
                if (compatibleWrapper(getter, setter)) {
                    Converter convert = new Converter(setter.getPropertyType(), SOURCE, readMethod.getName());
                    String f = convert.convert();
                    if (f != null) {
                        if (isWrapClass(getter.getPropertyType())) {
                            String source = genPropertySource(writerMethodName, f, true);
                            propSources.add(source);
                        } else {
                            propSources.add(genPropertySource(writerMethodName, f, false));
                        }
                        continue;
                    }
                }
                warnCantConvert(setter, getter);
            }
        }
        return propSources;
    }

    private String genCheckWrapperIsNotNullSource(String readName) {
        return "\tif(" + SOURCE + "." + readName + "() != null)\n";
    }

    private String genPropertySource(String writerMethodName, String getterSource, boolean isCheckNull) {
        String setMethod = "\t" + TARGET + "." + writerMethodName + "(" + getterSource + ");\n";
        if (!isCheckNull) {
            return setMethod;
        } else {
            return "\tif(" + getterSource + " != null)\n" + "{" + setMethod + "};\n";

        }
    }

    private void warnCantConvert(PropertyDescriptor setter, PropertyDescriptor getter) {

        System.out.println("[Properties] convert fail " +
                getter.getReadMethod().getDeclaringClass().getSimpleName() +
                "." +
                getter.getName() +
                "(" +
                getter.getPropertyType() +
                ") -> " +
                setter.getWriteMethod().getDeclaringClass().getSimpleName() +
                "." +
                setter.getName() +
                "(" +
                setter.getPropertyType() +
                ").");

    }

    /**
     * 检查是否可以生成源代码
     */
    private boolean checkCanGenSource(PropertyDescriptor setter, PropertyDescriptor getter) {
        // 是否被忽略
        if (ignoreProperties != null && isIgnoredProperty(setter)) {
            return false;
        }
        // 检查getter是否存在
        if (getter == null || getter.getReadMethod() == null) {
            return false;
        }
        // 检查setter的写方法是否存在
        return !(setter == null || setter.getWriteMethod() == null);
    }

    private boolean compatibleWrapper(PropertyDescriptor getter, PropertyDescriptor setter) {

        return isWrapClass(getter.getPropertyType(), setter.getPropertyType())
                || isWrapClass(setter.getPropertyType(), getter.getPropertyType());
    }

    private boolean isIgnoredProperty(PropertyDescriptor descriptor) {
        String propertyName = descriptor.getName();
        for (String ignoreProperty : ignoreProperties) {
            if (ignoreProperty.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }

    public Class<Copy> generate() {
        StringBuilder copybuilder = new StringBuilder();

        //生产COPY代码
        copybuilder.append(generateBegin("copy"));
        for (String ps : generateBody(false)) {
            copybuilder.append(ps);
        }

        copybuilder.append(generateEnd());
        //生产merge代码
        StringBuilder mergebuilder = new StringBuilder();

        mergebuilder.append(generateBegin("merge"));
        for (String ps : generateBody(true)) {
            mergebuilder.append(ps);
        }
        generateEnd();

        mergebuilder.append(generateEnd());

        ClassPool pool = ClassPool.getDefault();
        /**
         * The default ClassPool returned by a static method ClassPool.getDefault() searches the same path that
         * the underlying JVM (Java virtual machine) has. If a program is running on a web application server
         * such as JBoss and Tomcat, the ClassPool object may not be able to find user classes since such
         * a web application server uses multiple class loaders as well as the system class loader. In that case,
         * an additional class path must be registered to the ClassPool.
         */
        ClassClassPath classPath = new ClassClassPath(getClass());
        pool.insertClassPath(classPath);
        CtClass cc = pool.makeClass(packageName + ".CopierImpl" + classNameIndex.incrementAndGet());

        try {
            cc.addInterface(pool.get(Copy.class.getName()));
            CtMethod copym = CtNewMethod.make(copybuilder.toString(), cc);
            cc.addMethod(copym);
            CtMethod mergem = CtNewMethod.make(mergebuilder.toString(), cc);
            cc.addMethod(mergem);
            // noinspection unchecked
            return cc.toClass(getDefaultClassLoader(), null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
        return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
    }

    private ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ignored) {
        }
        if (cl == null) {
            cl = getClass().getClassLoader();
        }
        return cl;
    }

    public PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            return beanInfo.getPropertyDescriptors();
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        }
    }
}