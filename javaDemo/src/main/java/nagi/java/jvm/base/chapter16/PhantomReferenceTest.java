package nagi.java.jvm.base.chapter16;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用 不影响对象生命周期，用于追踪垃圾回收
 */
public class PhantomReferenceTest {
    static ReferenceQueue<PhantomReferenceTest> referenceQueue;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                if (referenceQueue != null) {
                    PhantomReference<PhantomReferenceTest> object = null;
                    try {
                        object = (PhantomReference<PhantomReferenceTest>) referenceQueue.remove();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (object != null) {
                        System.out.println("instance is cleaned");
                    }
                }
            }
        });
        thread.setDaemon(true);
        thread.start();

        referenceQueue = new ReferenceQueue<>();
        // 创建obj的虚引用，指定引用队列
        PhantomReference<PhantomReferenceTest> reference = new PhantomReference<>(new PhantomReferenceTest(), referenceQueue);
        System.gc();
        Thread.sleep(1000);
    }
}
