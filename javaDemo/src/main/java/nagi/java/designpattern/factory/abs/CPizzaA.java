package nagi.java.designpattern.factory.abs;

public class CPizzaA extends Pizza {

    public CPizzaA(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("Prepare pizza " + getName());
    }
}
