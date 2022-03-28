package nagi.java.designpattern.singleton;

public class LazySync {
    public static void main(String[] args) {

    }
}

//懒汉式
//解决线程安全问题 读操作带锁，效率低
class Singleton3 {
    //构造器私有化，外部不能new
    private Singleton3() {}

    private static Singleton3 instance;

    //提供一个公有静态方法，使用时创建实例
    public static synchronized Singleton3 getInstance() {
        if (instance == null) {
            instance = new Singleton3();
        }
        return instance;
    }
}
