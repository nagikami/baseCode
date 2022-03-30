package nagi.java.designpattern.factory.abs;

public class Order {
    public void order(AbsFactory absFactory, String type) {
        absFactory.createPizza(type);
    }
}
