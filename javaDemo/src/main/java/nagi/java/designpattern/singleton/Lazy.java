package nagi.java.designpattern.singleton;

public class Lazy {
    public static void main(String[] args) {

    }
}

//懒汉式
//多线程不可用，可能创建多个实例
class Singleton2 {
    //构造器私有化，外部不能new
    private Singleton2() {}

    private static Singleton2 instance;

    //提供一个公有静态方法，使用时创建实例
    public static Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }
}
