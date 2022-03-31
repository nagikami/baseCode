package nagi.java.designpattern.bridge;

/**
 * 桥接模式（分离抽象和实现） 通过抽象类聚合实现器（具体实现的接口），子类继承抽象类
 * jdbc的DriverManager使用桥接模式
 */
public class Client {
    public static void main(String[] args) {
        new FolderPhone(new Xiaomi()).open();
        new FolderPhone(new Huawei()).open();
    }
}
