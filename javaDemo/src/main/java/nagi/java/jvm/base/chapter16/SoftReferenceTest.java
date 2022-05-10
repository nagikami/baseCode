package nagi.java.jvm.base.chapter16;

import java.lang.ref.SoftReference;

/**
 * 软引用 内存不足即回收 常用于缓存
 * -Xms10m -Xmx10m -XX:+PrintGCDetails
 */
public class SoftReferenceTest {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建软引用，等价于
         * User user = new User("nagi", "1");
         * SoftReference<User> userSoftReference = new SoftReference<>(user); // 创建软引用
         * user = null; // 取消强引用
         */
        SoftReference<User> userSoftReference = new SoftReference<>(new User("nagi", "1"));

        System.gc();
        Thread.sleep(3000);
        // 获取软引用对象
        System.out.println(userSoftReference.get());

        try {
            // 占用资源，使内存不足
            byte[] bytes = new byte[7 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // OOM前，内存不足时二次回收软引用可达对象
            System.out.println(userSoftReference.get());
        }
    }

    static class User{
        private String name;
        private String id;

        public User(String name, String id) {
            this.name = name;
            this.id = id;
        }

        @Override
        public String toString() {
            return "name: " + name + " id: " + id;
        }
    }
}
