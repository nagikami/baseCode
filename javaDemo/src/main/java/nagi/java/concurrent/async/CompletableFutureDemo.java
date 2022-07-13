package nagi.java.concurrent.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //异步无返回值
        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " execute without return");
        });

        System.out.println(voidCompletableFuture.get());

        //异步有返回值
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + " execute with return");
            return 1000;
        });

        Thread.sleep(1000);
        //注册执行完成的回调函数（添加子任务到任务栈），处理返回值， r执行结果 e 异常
        integerCompletableFuture.whenComplete((r, e) -> {
            System.out.println("r " + r);
            System.out.println("e " + e);
        });
        System.out.println(integerCompletableFuture.get());
    }
}
