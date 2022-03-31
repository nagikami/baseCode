package nagi.java.designpattern.decorator;

public class Milk extends Drink {

    public Milk() {
        setDes("milk");
        setPrice(5);
    }

    @Override
    public double cost() {
        return price;
    }
}
