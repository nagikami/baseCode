package nagi.java.designpattern.singleton;

public class LazyStaticInner {
    public static void main(String[] args) {

    }
}

//懒汉式（静态内部类) 推荐
//装载Singleton5时不会装载静态内部类
class Singleton5 {
    //构造器私有化，外部不能new
    private Singleton5() {}

    //类装载线程安全
    private static class SingletonInstance {
        private final static Singleton5 instance = new Singleton5();
    }

    //提供一个公有静态方法
    public static synchronized Singleton5 getInstance() {
        //访问静态内部类时，jvm装载类
        return SingletonInstance.instance;
    }
}
