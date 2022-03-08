package nagi.java.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class Share {
    private int number = 0;

    private  final ReentrantLock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    public void incr() throws InterruptedException {
        lock.lock();

        try {
            while (number != 0) {
                condition.await();
            }
            number++;
            System.out.println("Tread " + Thread.currentThread().getName() + " number " + number);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void decr() throws InterruptedException {
        lock.lock();

        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println("Tread " + Thread.currentThread().getName() + " number " + number);
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadCommunication {
    public static void main(String[] args) {
        Share share = new Share();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "DD").start();
    }
}
