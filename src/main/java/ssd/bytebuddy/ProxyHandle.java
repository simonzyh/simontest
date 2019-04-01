package ssd.bytebuddy;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.reflect.Method;

public class ProxyHandle {


    @RuntimeType
    public Object invoke(@Origin Method method, @AllArguments @RuntimeType Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();

        Object arg0 = args[0];
        if (returnType.equals(void.class)) {
            System.out.println(arg0 + "proxy====");
            return null;
        }

        return arg0 + "proxy====";
    }
}
