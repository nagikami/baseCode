package nagi.java.designpattern.singleton;

public class LazyDoubleCheck {
    public static void main(String[] args) {

    }
}

//懒汉式（双重检查）推荐
//解决线程安全问题实例化只执行一次
class Singleton4 {
    //构造器私有化，外部不能new
    private Singleton4() {}

    private static volatile Singleton4 instance;

    //提供一个公有静态方法，使用时创建实例
    public static synchronized Singleton4 getInstance() {
        //实例化一次
        if (instance == null) {
            synchronized (Singleton4.class) {
                //防并发，volatile可见性
                if (instance == null) {
                    instance = new Singleton4();
                }
            }
            instance = new Singleton4();
        }
        return instance;
    }
}
