package ssd.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

public class RequestCacheCommand extends HystrixCommand<String> {
    private final int id;

    public RequestCacheCommand(int id) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestCacheCommand"));
        this.id = id;
    }

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();

        RequestCacheCommand command2a = new RequestCacheCommand(2);
        RequestCacheCommand command2b = new RequestCacheCommand(2);
        System.out.println(command2a.execute());
        //isResponseFromCache判定是否是在缓存中获取结果
        System.out.println(command2a.isResponseFromCache());
        System.out.println(command2b.execute());
        System.out.println(command2b.isResponseFromCache());

        new Thread() {

            @Override
            public void run() {
                //HystrixRequestContext context = HystrixRequestContext.initializeContext();

                RequestCacheCommand command3b = new RequestCacheCommand(2);
                System.out.println("command3b " + command3b.execute());
                System.out.println("command3b " + command3b.isResponseFromCache());
            }
        }.start();

        // context = HystrixRequestContext.initializeContext();
        RequestCacheCommand command3b = new RequestCacheCommand(2);
        System.out.println(command3b.execute());
        System.out.println(command3b.isResponseFromCache());
        //  context.shutdown();
    }

    @Override
    protected String run() throws Exception {
        System.out.println(Thread.currentThread().getName() + " execute id=" + id);
        return "executed=" + id;
    }

    //重写getCacheKey方法,实现区分不同请求的逻辑
    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
}
