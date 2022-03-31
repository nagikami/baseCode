package nagi.java.designpattern.adapter.classadapter;

/**
 * adapter适配器模式，又称wrapper封装模式
 * SpringMVC的HandlerAdapter使用适配器模式，关联controller与其handler
 */
public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.charge(new VoltageAdapter());
    }
}
