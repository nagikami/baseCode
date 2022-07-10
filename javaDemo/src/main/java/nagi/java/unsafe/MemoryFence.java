package nagi.java.unsafe;

import sun.misc.Unsafe;

/**
 * 禁止跨屏障排序，但是屏障之间可以重排
 * loadFence = LoadLoad+LoadStore 保证屏障前的所有读操作完成，并使缓存失效，重新从主存读取
 * storeFence = StoreStore+LoadStore 保证屏障前的所有写操作完成，刷新缓存到主存，使其他缓存失效
 * fullFence = StoreStore+LoadStore+StoreLoad
 */
public class MemoryFence {
    public static void main(String[] args) {
        SubThread subThread = new SubThread();
        new Thread(subThread).start();
        Unsafe unsafe = UnsafeTest.getUnsafe();
        for(;;) {
            boolean flag = subThread.flag;
            // 使用读屏障，保证屏障前的所有读操作完成，并使缓存失效，重新从主存读取
            unsafe.loadFence();
            if (flag) {
                System.out.println("detect change of flag");
            }
        }
    }
}

class SubThread implements Runnable {
    // 非volatile，变量更新后不会使其他cpu缓存失效
    boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("Flag changed to true");
    }
}
