package nagi.java.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;

class Ticket {
    private int number = 30;

    //可重入锁
    private final ReentrantLock lock = new ReentrantLock();

    public void sale() {
        lock.lock();

        try {
            System.out.println("Thread " + Thread.currentThread().getName() + " sales one ticket, current " + (--number));
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTickets {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ticket.sale();
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ticket.sale();
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                ticket.sale();
            }
        }, "CC").start();
    }
}
