package nagi.java.designpattern.factory.simple;

public class PizzaA extends Pizza {

    public PizzaA(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("Prepare pizza " + getName());
    }
}
