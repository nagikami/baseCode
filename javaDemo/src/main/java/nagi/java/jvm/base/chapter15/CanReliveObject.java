package nagi.java.jvm.base.chapter15;

public class CanReliveObject {
    public static CanReliveObject object;

    public static void main(String[] args) throws InterruptedException {
        object = new CanReliveObject();

        object = null;
        System.gc();

        // finalizer优先级较低，等待执行
        Thread.sleep(2000);
        if (object == null) {
            System.out.println("Object is cleaned");
        } else {
            System.out.println("Object is still alive");
        }

        object = null;
        System.gc();
        Thread.sleep(2000);
        if (object == null) {
            System.out.println("Object is cleaned");
        } else {
            System.out.println("Object is still alive");
        }
    }

    /**
     * GC时调用，放到低优先级队列，只会调用一次
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("Invoke method finalize");
        // 带回收对象在finalize()方法中与引用链上的对象建立联系，重新复活
        object = this;
    }
}
