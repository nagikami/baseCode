package nagi.java.designpattern.factory.abs;

public class DPizzaB extends Pizza {

    public DPizzaB(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("Prepare pizza " + getName());
    }
}
