package forkjoin;


import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask {

    private static final int THRESHOLD = 1000000;//阈值

    private long start;

    private long end;

    public CountTask(long start, long end) {

        this.start = start;

        this.end = end;

    }


    protected Long compute() {

        long sum = 0;

        //如果任务足够小就计算任务

        boolean canCompute = (end - start) <= THRESHOLD;

        if (canCompute) {

            for (long i = start; i <= end; i++) {

                sum += i;

            }

        } else {

            //如果任务大于阀值，就分裂成两个子任务计算

            Long middle = (start + end) / 2;

            CountTask leftTask = new CountTask(start, middle);

            CountTask rightTask = new CountTask(middle + 1, end);

            //执行子任务

            leftTask.fork();

            rightTask.fork();

            //等待子任务执行完，并得到其结果

            Long leftResult = (Long) leftTask.join();

            Long rightResult = (Long) rightTask.join();

            //合并子任务

            sum = leftResult + rightResult;

        }
        // System.out.println(Thread.currentThread().getId()+" start="+start+" end="+end);
        return sum;

    }

}