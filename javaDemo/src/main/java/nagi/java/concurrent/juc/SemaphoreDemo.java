package nagi.java.concurrent.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 使用share mode，详见AbstractQueuedSynchronizer
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //创建semaphore对象，设置许可数
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();

                    System.out.println("Thread " + Thread.currentThread().getName() + " acquire");

                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));

                    System.out.println("Thread " + Thread.currentThread().getName() + " leaved");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
