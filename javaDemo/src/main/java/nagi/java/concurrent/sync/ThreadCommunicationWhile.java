package nagi.java.concurrent.sync;

//第一步 创建资源类，在资源类创建属性和方法
class ShareWhile {
    private int number = 0;

    public synchronized void incr() throws InterruptedException {
        //第二步 判断 工作 通知
        //第四步 使用while避免虚假唤醒问题
        while (number != 0) {
            this.wait();
        }

        number++;

        System.out.println("Tread " + Thread.currentThread().getName() + " number " + number);

        this.notifyAll();
    }

    public synchronized void decr() throws InterruptedException {
        while (number == 0) {
            this.wait();
        }

        number--;

        System.out.println("Tread " + Thread.currentThread().getName() + " number " + number);

        this.notifyAll();
    }
}

//第三步 创建多个线程，调用资源类的操作方法
public class ThreadCommunicationWhile {
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
