package ssd.test;

import java.util.concurrent.*;
import java.util.function.Function;

public class Call2Test {
    //异步执行任务的poll
    private static ExecutorService executor = Executors.newFixedThreadPool(20); //使用线程池

    /**
     * 执行调用
     *
     * @param function
     * @param param
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> CallContext<T, R> call(Function<T, R> function, T param) {
        CallContext<T, R> callContext = new CallContext<T, R>(function, param);
        //提交任务
        CallRunnable<T, R> callRunnable = new CallRunnable(callContext);
        executor.submit(callRunnable);
        return callContext;
    }

    /**
     * test
     * 每个调用耗时1S， 不并行调用总耗时2s，并行后总耗时会少于2s
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Long lstart = System.currentTimeMillis();


        CallContext<Integer, String> queryUserFormDbResult = call(CallTest.queryUserFormDb, 100);

        CallContext<Integer, String> queryUserFormRpcResult = call(CallTest.queryUserFormDb, 200);

        // 这里实际应用上可以加入超时的逻辑在里面
        String userName1 = queryUserFormDbResult.getResponse();

        String userName2 = queryUserFormRpcResult.getResponse();

        System.out.println("调用结束：userName1=" + userName1 + " userName2=" + userName2 + " time=" + (System.currentTimeMillis() - lstart));

    }
}

/**
 * 任务封装
 */
class CallRunnable<T, R> implements Runnable {
    CallContext<T, R> callContext;

    CallRunnable(CallContext<T, R> callContext) {
        this.callContext = callContext;
    }

    @Override
    public void run() {
        try {
            R r = callContext.function.apply(callContext.param);
            //执行完成回掉上下文的setresponse
            callContext.succ(r);
        } catch (Exception e) {
            //调用失败执行失败回掉
            callContext.fail(e);
        }
    }
}

/**
 * 调用上下文
 */
class CallContext<T, R> {
    CountDownLatch countDownLatch = new CountDownLatch(1);
    Function<T, R> function;
    T param;
    private long timeOut = 60;
    private R r;

    /**
     * 是否已经完成 0 未完成  1 已经完成 -1 timeout
     */

    private int status = 0;

    CallContext(Function<T, R> function, T param) {
        this.function = function;
        this.param = param;
    }

    public R getResponse() throws InterruptedException {
        countDownLatch.await(timeOut, TimeUnit.SECONDS);
        if (status != 1) {
            status = -1;//将状态设置为-1  等待异步清除
            throw new RuntimeException("调用超时");
        }

        return r;
    }

    /**
     * 执行完成回掉
     *
     * @param r
     */
    public void succ(R r) {
        status = 1;
        this.r = r;
        countDownLatch.countDown();
    }

    /**
     * 调用失败
     */
    public void fail(Exception e) {
        status = 0;
        countDownLatch.countDown();
        // log.error(e) 这里可以抛出exception 现在暂时不处理
    }
}


