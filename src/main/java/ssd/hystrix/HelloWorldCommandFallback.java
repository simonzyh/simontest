package ssd.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.TimeUnit;

//重载HystrixCommand 的getFallback方法实现逻辑
public class HelloWorldCommandFallback extends HystrixCommand<String> {
    private final String name;

    public HelloWorldCommandFallback(String name) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                /* 配置依赖超时时间,500毫秒*/
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationThreadTimeoutInMilliseconds(50)));
        this.name = name;
    }

    public static void main(String[] args) throws Exception {
        HelloWorldCommandFallback command = new HelloWorldCommandFallback("test-Fallback");
        String result = command.execute();
        command.observe();
        System.out.println("result=" + result);
    }

    @Override
    protected String getFallback() {
        return "exeucute Falled";
    }

    @Override
    protected String run() throws Exception {
        //sleep 1 秒,调用会超时
        TimeUnit.MILLISECONDS.sleep(1000);
        return "Hello " + name + " thread:" + Thread.currentThread().getName();
    }
}
/* 运行结果:getFallback() 调用运行
getFallback executed
*/