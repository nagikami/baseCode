package nagi.java.designpattern.singleton;

public class eagerStaticCode {
    public static void main(String[] args) {
        Singleton1 instance1 = Singleton1.getInstance();
        Singleton1 instance2 = Singleton1.getInstance();
        System.out.println(instance1 == instance2);
    }
}

//饿汉式（静态变量）
//优点：类装载时实例化，避免线程同步问题 缺点：没有实现懒加载，可能造成内存浪费
class Singleton1 {
    //构造器私有化，外部不能new
    private Singleton1() {}

    //类内部创建对象实例
    private static Singleton1 instance;

    static {
        //静态代码块赋值
        instance = new Singleton1();
    }

    //提供一个公有静态方法，返回实例
    public static Singleton1 getInstance() {
        return instance;
    }
}
