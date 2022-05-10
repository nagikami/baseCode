package nagi.java.jvm.base.chapter16;

import java.lang.ref.WeakReference;

/**
 * 弱引用 发现即回收
 * 只被弱引用引用的对象只能生存到下一次垃圾收集（优先级较低，对象可能生存较长时间）为止
 * WeakHashMap
 */
public class WeakReferenceTest {
    public static void main(String[] args) throws InterruptedException {
        WeakReference<User> user = new WeakReference<>(new User("nagi", "1"));
        System.out.println(user.get());
        System.gc();

        Thread.sleep(3000);
        System.out.println("After gc");
        // 重新获取引用对象
        System.out.println(user.get());
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
