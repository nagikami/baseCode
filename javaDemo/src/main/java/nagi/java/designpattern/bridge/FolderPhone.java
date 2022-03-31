package nagi.java.designpattern.bridge;

public class FolderPhone extends Phone {
    public FolderPhone(Brand brand) {
        super(brand);
    }

    //最终调用接口实现类方法
    public void open() {
        super.open();
        System.out.println("Folder phone");
    }

    public void close() {
        super.close();
        System.out.println("Folder phone");
    }

    public void call() {
        super.call();
        System.out.println("Folder phone");
    }
}
