package forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * Created by yehua.zyh on 2016/5/18.
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("https://actmanager.tmall.com/subject/preview.do?channel$1&".replaceAll("\\$", "="));
        System.out.println("https://actmanager.tmall.com/subject/preview.do?channel$1&".replace("$", "="));

        ForkJoinPool forkJoinPool = new ForkJoinPool(50);

        //生成一个计算任务，负责计算1+2+3+4
        Long l1 = System.currentTimeMillis();
        CountTask task = new CountTask(1, 4000000000L);
        task.fork();
        //执行一个任务

        System.out.println(task.join() + " " + (System.currentTimeMillis() - l1));
        CountTask task2 = new CountTask(1, 4000000000L);
        Long l2 = System.currentTimeMillis();
        Future result = forkJoinPool.submit(task2);
        System.out.println(result.get() + " " + (System.currentTimeMillis() - l2));

    }
}
