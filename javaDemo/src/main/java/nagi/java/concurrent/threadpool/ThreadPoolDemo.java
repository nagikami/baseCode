package nagi.java.concurrent.threadpool;

import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) {
        //one pool N threads
        executeTasks(Executors.newFixedThreadPool(10));
        //one pool one thread
        executeTasks(Executors.newSingleThreadExecutor());
        //one extensive threads
        executeTasks(Executors.newCachedThreadPool());

        //customize thread pool 推荐使用，上述三种方法或有oom风险（1,2允许的队列长度和3允许的线程数据量都为最大int）
        executeTasks(new ThreadPoolExecutor(2, 5, 2L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy()));
    }

    private static void executeTasks(ExecutorService executorService) {
        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}
