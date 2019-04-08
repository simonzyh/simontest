package ssd.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogHandler implements InvocationHandler {


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //doBefore();
        //在这里完全可以把下面这句注释掉，而做一些其它的事情
        Object result = null;//method.invoke(dele, args);
        //System.out.println("参数：" + java.util.Arrays.toString(args) + " method== " + method.toString() + "  " + method.getGenericReturnType());
        //after();

        return result;
    }

    private void doBefore() {
        System.out.println("before....");
    }

    private void after() {
        System.out.println("after....");
    }
}