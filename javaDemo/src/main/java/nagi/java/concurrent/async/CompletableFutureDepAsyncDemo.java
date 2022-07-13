package nagi.java.concurrent.async;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureDepAsyncDemo {
    public static void main(String[] args) {
        // 默认提交到ForkJoinPool.commonPool线程池执行，线程都设置为daemon
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                // 保证子任务都入栈
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Task thread: " + Thread.currentThread().getName());
            return 1000;
        });
        CompletableFuture<Integer> whenComplete = integerCompletableFuture.whenCompleteAsync((result, exception) -> {
            System.out.println("whenComplete Task thread: " + Thread.currentThread().getName());
            System.out.println("whenComplete result: " + result);
            System.out.println("whenComplete exception: " + exception);
        });
        // 依赖子任务的任务FIFO
        whenComplete.whenCompleteAsync((result, exception) -> {
            System.out.println("whenComplete1 Task thread: " + Thread.currentThread().getName());
            System.out.println("whenComplete1 result: " + result);
            System.out.println("whenComplete1 exception: " + exception);
        });
        whenComplete.thenApplyAsync(result -> {
            System.out.println("thenApply Task thread: " + Thread.currentThread().getName());
            System.out.println("thenApply result: " + result);
            return result;
        });
        // 子任务LIFO
        integerCompletableFuture.whenCompleteAsync((result, exception) -> {
            System.out.println("whenComplete2 Task thread: " + Thread.currentThread().getName());
            System.out.println("whenComplete2 result: " + result);
            System.out.println("whenComplete2 exception: " + exception);
        });
        try {
            // 保证异步任务执行完成，防止main先执行完，只剩下未执行完的daemon线程池线程导致程序退出
            System.out.println(integerCompletableFuture.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
