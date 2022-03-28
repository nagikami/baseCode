package nagi.java.designpattern.singleton;

public class eagerStaticV {
    public static void main(String[] args) {
        Singleton instance1 = Singleton.getInstance();
        Singleton instance2 = Singleton.getInstance();
        System.out.println(instance1 == instance2);
    }
}

//饿汉式（静态变量）
//优点：类装载时实例化，避免线程同步问题 缺点：没有实现懒加载，可能造成内存浪费
class Singleton {
    //构造器私有化，外部不能new
    private Singleton() {}

    //类内部创建对象实例
    private static final Singleton instance = new Singleton();

    //提供一个公有静态方法，返回实例
    public static Singleton getInstance() {
        return instance;
    }
}
