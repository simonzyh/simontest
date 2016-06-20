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
public   class Generator {
    private static final AtomicInteger classNameIndex = new AtomicInteger(1000);
    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);
    private static final String packageName = Copier.class.getPackage().getName();

    private final String SOURCE = "s";
    private final String TARGET = "t";
    private Class source;
    private Class target;
    private String[] ignoreProperties;

    private String beginSource;
    private List<String> propSources = Lists.newArrayList();
    private String endSources;

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

    private void generateBegin() {
        // 生成方法签名public void copy(Object s1, Object t1) {
        beginSource = "public void copy(Object " + SOURCE + "1, Object " + TARGET + "1) {\n";
        // 强制转换源对象
        String convertSource = "\t" + source.getName() + " " + SOURCE + " = " + "(" + source.getName() + ")" + SOURCE + "1;\n";
        // 强制转换目标对象
        String convertTarget = "\t" + target.getName() + " " + TARGET + " = " + "(" + target.getName() + ")" + TARGET + "1;\n";
        beginSource += convertSource + convertTarget;
        System.out.println(beginSource);
    }

    private void generateEnd() {
        endSources = "}";
    }

    private void generateBody() {
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
                propSources.add(genPropertySource(writerMethodName, SOURCE + "." + readMethodName + "()"));
            } else {
                // 是否是包装类转换
                if (compatibleWrapper(getter, setter)) {
                    Converter convert = new Converter(setter.getPropertyType(), SOURCE, readMethod.getName());
                    String f = convert.convert();
                    if (f != null) {
                        if (isWrapClass(getter.getPropertyType())) {
                            String source = genCheckWrapperIsNotNullSource(readMethod.getName());
                            source += "\t" + genPropertySource(writerMethodName, f);
                            propSources.add(source);
                        } else {
                            propSources.add(genPropertySource(writerMethodName, f));
                        }
                        continue;
                    }
                }
            }
           // warnCantConvert(setter, getter);

        }
        System.out.println("propSources="+propSources);

    }

    private String genCheckWrapperIsNotNullSource(String readName) {
        return "\tif(" + SOURCE + "." + readName + "() != null)\n";
    }

    private String genPropertySource(String writerMethodName, String getterSource) {
        return "\t" + TARGET + "." + writerMethodName + "(" + getterSource + ");\n";
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
        generateBegin();
        generateBody();
        generateEnd();

        StringBuilder builder = new StringBuilder();
        builder.append(beginSource);
        for (String ps : propSources) {
            builder.append(ps);
        }
        builder.append(endSources);
        String source = builder.toString();
        System.out.println("source="+source);
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
            CtMethod m = CtNewMethod.make(source, cc);
            cc.addMethod(m);
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
        } catch (Throwable ignored) {}
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
}