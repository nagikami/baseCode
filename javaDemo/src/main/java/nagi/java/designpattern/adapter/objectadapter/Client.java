package nagi.java.designpattern.adapter.objectadapter;

/**
 * adapter适配器模式，又称wrapper封装模式
 */
public class Client {
    public static void main(String[] args) {
        Phone phone = new Phone();
        phone.charge(new VoltageAdapter(new Voltage220()));
    }
}
