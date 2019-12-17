package ssd.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class ProxyHandle {


    @RuntimeType
    public Object invoke(@Origin Method method,
                         @SuperCall Callable<?> callable,
                         @AllArguments
                         @RuntimeType Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();

        Object arg0 = args[0];
        System.out.println(callable.getClass().getName());
        if (returnType.equals(void.class)) {
            System.out.println(arg0 + "proxy====");
            return null;
        }

        return arg0 + "proxy====";
    }
}
