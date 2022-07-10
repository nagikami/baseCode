package nagi.java.unsafe;

import sun.misc.Unsafe;

public class VolatileTest {
    public static void main(String[] args) {
        SubThreadV subThread = new SubThreadV();
        new Thread(subThread).start();
        Unsafe unsafe = UnsafeTest.getUnsafe();
        for(;;) {
            boolean flag = subThread.flag;
            // 保证volatile读操作不会和之后的读写操作重排序 LoadLoad+LoadStore
            unsafe.loadFence();
            if (flag) {
                System.out.println("detect change of flag");
            }
        }
    }
}

class SubThreadV implements Runnable {
    boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Unsafe unsafe = UnsafeTest.getUnsafe();
        // 保证volatile写操作不会和之前的写操作重排序 StoreStore
        unsafe.storeFence();
        flag = true;
        // 保证volatile写操作不会和之后的读操作重排序 StoreLoad
        unsafe.fullFence();
        System.out.println("Flag changed to true");
    }
}
