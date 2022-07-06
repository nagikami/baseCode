package nagi.java.concurrent.juc;

import java.util.concurrent.CountDownLatch;

/**
 * 使用share mode，详见AbstractQueuedSynchronizer
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        // 初始化state为传入值
        CountDownLatch countDownLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println("Student " + Thread.currentThread().getName() + " leaves");
                // releaseShared(1) state--
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        // 阻塞线程，直到state为0时被唤醒
        countDownLatch.await();
        System.out.println("The door is locked");
    }
}
