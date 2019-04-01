package ssd.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.springframework.beans.factory.FactoryBean;

public class TestFactoryBean<T> implements FactoryBean<T> {

    private Class<T> tClass;

    public static void main(String[] args) {
        ProxyHandle proxyHandle = new ProxyHandle();
        TestService testService = newProxy(TestService.class, proxyHandle);
        testService.test1("123");
        System.out.println(testService.test2("456"));
    }

    public static <T> T newProxy(Class<T> interfaceType, Object handler) {
        Class<? extends T> cls = new ByteBuddy()
                .subclass(interfaceType)
                .method(ElementMatchers.named("test2"))
                .intercept(MethodDelegation.to(handler, "handler"))
                .make()
                .load(interfaceType.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                .getLoaded();

        try {
            return cls.newInstance();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null; // never get here
    }

    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
