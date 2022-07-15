package nagi.java.concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

class MyTask extends RecursiveTask<Integer> {
    private static final Integer Value = 10;
    private int begin;
    private int end;
    private int result;

    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if ((end - begin) < Value) {
            for (int i = begin; i <= end; i++) {
                result += i;
            }
        } else {
            int middle = (end + begin) / 2;
            MyTask myTask1 = new MyTask(begin, middle);
            MyTask myTask2 = new MyTask(middle + 1, end);

            //创建分支
            myTask1.fork();
            myTask2.fork();

            //合并分支，最好和fork顺序对称，join时会先尝试获取任务队列top的任务，对称可提高效率
            result = myTask2.join() + myTask1.join();
        }
        return result;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyTask myTask = new MyTask(0, 100);

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        ForkJoinTask<Integer> submit = forkJoinPool.submit(myTask);

        //获取合并结果
        Integer result = submit.get();

        System.out.println(result);

        forkJoinPool.shutdown();
    }
}
