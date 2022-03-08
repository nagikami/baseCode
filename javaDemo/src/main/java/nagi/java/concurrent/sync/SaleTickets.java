package nagi.java.concurrent.sync;

//第一步 创建资源类，在资源类创建属性和方法
class Ticket {

    private int number = 30;

    public synchronized void sale() {
        if (number > 0) {
            System.out.println("Thread " + Thread.currentThread().getName() + " sales one ticket, current " + (--number));
        }
    }
}

public class SaleTickets {
    //第二步 创建多个线程，调用资源类的操作方法
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ticket.sale();
                }
            }
        }, "AA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ticket.sale();
                }
            }
        }, "BB").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ticket.sale();
                }
            }
        }, "CC").start();
    }
}
