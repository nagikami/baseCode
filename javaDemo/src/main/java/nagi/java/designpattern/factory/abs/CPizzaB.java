package nagi.java.designpattern.factory.abs;

public class CPizzaB extends Pizza {

    public CPizzaB(String name) {
        super(name);
    }

    @Override
    public void prepare() {
        System.out.println("Prepare pizza " + getName());
    }
}
