package nagi.java.designpattern.bridge;

public class Huawei implements Brand {
    @Override
    public void call() {
        System.out.println("huawei call");
    }

    @Override
    public void open() {
        System.out.println("huawei open");
    }

    @Override
    public void close() {
        System.out.println("huawei close");
    }
}
