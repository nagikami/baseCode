package nagi.java.designpattern.factory.abs;

public class DPizzaA extends Pizza {

    public DPizzaA(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("Prepare pizza " + getName());
    }
}
