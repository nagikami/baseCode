package nagi.java.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ShareOrder {
    private int flag = 1;

    ReentrantLock lock = new ReentrantLock();

    //通过不同的condition区分不同的线程组
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void print1(int loop) throws InterruptedException {
        lock.lock();
        try {
            while (flag != 1) {
                //等待线程组1被唤醒
                condition1.await();
            }

            System.out.println("Thread " + Thread.currentThread().getName() + " :: " + loop);

            flag = 2;
            //唤醒线程组2
            condition2.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void print2(int loop) throws InterruptedException {
        lock.lock();
        try {
            while (flag != 2) {
                condition2.await();
            }

            System.out.println("Thread " + Thread.currentThread().getName() + " :: " + loop);

            flag = 3;
            condition3.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void print3(int loop) throws InterruptedException {
        lock.lock();
        try {
            while (flag != 3) {
                condition3.await();
            }

            System.out.println("Thread " + Thread.currentThread().getName() + " :: " + loop);

            flag = 1;
            condition1.signalAll();
        } finally {
            lock.unlock();
        }
    }
}

public class ThreadCommunicationOrder {
    public static void main(String[] args) {
        ShareOrder shareOrder = new ShareOrder();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareOrder.print1(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareOrder.print2(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    shareOrder.print3(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }
}
