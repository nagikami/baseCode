package nagi.java.designpattern.strategy;

public class CanFly implements Flyable{
    @Override
    public void fly() {
        System.out.println("Can fly");
    }
}
