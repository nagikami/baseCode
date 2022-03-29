package nagi.java.designpattern.factory.simple;

public class PizzaB extends Pizza {

    public PizzaB(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("Prepare pizza " + getName());
    }
}
