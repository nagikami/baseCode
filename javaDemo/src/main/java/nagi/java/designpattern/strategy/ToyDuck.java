package nagi.java.designpattern.strategy;

public class ToyDuck extends Duck{

    public ToyDuck() {
        this.flyable = new NoFly();
    }

    @Override
    public void display() {
        System.out.println("toy duck");
    }
}
