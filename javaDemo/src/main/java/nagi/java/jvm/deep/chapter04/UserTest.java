package nagi.java.jvm.deep.chapter04;

/**
 * -XX:+TraceClassLoading 打印类加载信息
 */
public class UserTest {
    public static void main(String[] args) throws ClassNotFoundException {
        // 隐式加载
        User user = new User();

        // 显示加载
        Class<?> aClass = Class.forName("nagi.java.jvm.deep.chapter04.User");
        Class<?> aClass1 = ClassLoader.getSystemClassLoader().loadClass("nagi.java.jvm.deep.chapter04.User");
    }
}
