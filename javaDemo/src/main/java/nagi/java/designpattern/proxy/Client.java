package nagi.java.designpattern.proxy;

/**
 * 静态代理 代理类和被代理类实现相同的接口 代理类持有一个被代理类的实例
 * 动态代理 运行时动态生成代理类 jdk是jvm动态生成Proxy子类 cglib是使用ASM修改被代理类字节生成代理类
 */
public class Client {
    public static void main(String[] args) {
        new TeacherProxy(new Teacher()).teach();
    }
}
