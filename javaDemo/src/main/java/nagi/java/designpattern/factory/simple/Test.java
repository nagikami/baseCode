package nagi.java.designpattern.factory.simple;

public class Test {
    public static void main(String[] args) {
        Order order = new Order();
        order.order("A");
        order.order("B");
    }
}
