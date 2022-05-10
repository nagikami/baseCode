package nagi.java.jvm.base.chapter16;

/**
 * 强引用 引用存在不会回收
 */
public class StrongReferenceTest {
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer("hello nagi");
        StringBuffer buffer1 = buffer;
        buffer = null;

        System.gc();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(buffer1);
    }
}
