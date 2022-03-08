package nagi.java.concurrent.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    //设置固定值
    public static final int number = 7;

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(number, () -> {
            System.out.println("All tasks completed");
        });

        for (int i = 0; i < 7; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Task "+ Thread.currentThread().getName() + " completed");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
