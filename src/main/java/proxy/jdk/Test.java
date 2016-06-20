package proxy.jdk;

import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
         LogHandler handler = new LogHandler();
        //这里把handler与impl新生成的代理类相关联  
        Hello hello = (Hello) Proxy.newProxyInstance(Hello.class.getClassLoader(), new Class[]{Hello.class}, handler);

        hello.print("All the test");
        hello.sayHello("Denny");
    }

}  