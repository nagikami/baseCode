package nagi.srccode.ribbon;

import java.util.concurrent.atomic.AtomicInteger;

public class CASLock {
    private final AtomicInteger incrementer = new AtomicInteger(0);

    /**
     * 通过CAS自旋实现线程安全自增器
     */
    public void incrementWithCAS() {
        for (;;) {
            int oldValue = incrementer.get();
            int current = oldValue + 1;
            if (incrementer.compareAndSet(oldValue, current)) {
                System.out.println(current);
                return;
            }
        }
    }

    /**
     * 线程不安全
     */
    public void increment() {
        int i = incrementer.get();
        incrementer.set(i++);
        System.out.println(i);
    }

    public static void main(String[] args) {
        CASLock casLock = new CASLock();
        for (int i = 0; i < 10; i++) {
            new Thread(casLock::incrementWithCAS).start();
        }
    }
}
