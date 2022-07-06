package nagi.java.concurrent.sync;

//第一步 创建资源类，在资源类创建属性和方法
class Share {
    private int number = 0;

    public synchronized void incr() throws InterruptedException {
        //第二步 判断 工作 通知
        //if判断，两个以上线程存在虚假唤醒（可能连续两次都是相同操作的线程获得锁）问题，应使用while
        if (number != 0) {
            this.wait();
        }

        number++;

        System.out.println("Tread " + Thread.currentThread().getName() + " number " + number);

        this.notifyAll();
    }

    public synchronized void decr() throws InterruptedException {
        if (number == 0) {
            this.wait();
        }

        number--;

        System.out.println("Tread " + Thread.currentThread().getName() + " number " + number);

        this.notifyAll();
    }
}

//第三步 创建多个线程，调用资源类的操作方法
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
                    share.decr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();
    }
}
