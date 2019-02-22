import java.util.concurrent.*;
import java.util.function.Function;

/**
 * Future 实现多个任务异步调用
 */
public class CallTest {
    //异步执行任务的poll
    private static ExecutorService executor = Executors.newFixedThreadPool(20); //使用线程池

    /**
     * 提交一个任务 任务以function的现实
     *
     * @param function 需要执行的调用
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> FutureTask<R> call(Function<T, R> function, T param) {
        if (null == function) {
            return null;// or 抛出指定的exxception
        }
        FutureTask<R> futureTask = new FutureTask<R>((Callable<R>) () -> function.apply(param)
        );
        executor.submit(futureTask);
        return futureTask;
    }

    /**
     * test
     * 每个调用耗时1S， 不并行调用总耗时2s，并行后总耗时会少于2s
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Long lstart = System.currentTimeMillis();


        FutureTask<String> queryUserFormDbResult = call(queryUserFormDb, 100);

        FutureTask<String> queryUserFormRpcResult = call(queryUserFormDb, 200);

        // 这里实际应用上可以加入超时的逻辑在里面
        String userName1 = queryUserFormDbResult.get();

        String userName2 = queryUserFormRpcResult.get();

        System.out.println("调用结束：userName1=" + userName1 + " userName2=" + userName2 + " time=" + (System.currentTimeMillis() - lstart));

    }

    //从db查询数据
      static Function<Integer, String> queryUserFormDb = new Function<Integer, String>() {
        @Override
        public String apply(Integer integer) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //此处查询数据库操作
            return "数据库返回：" + integer + "--" + System.currentTimeMillis();
        }
    };
    //从rpc查询数据
      static Function<Integer, String> queryUserFormRpc = new Function<Integer, String>() {
        @Override
        public String apply(Integer integer) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //此处查询数据库操作
            return "RPC返回  ：" + integer + "--" + System.currentTimeMillis();
        }
    };

}
