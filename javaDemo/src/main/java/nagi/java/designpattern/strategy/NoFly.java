package nagi.java.designpattern.strategy;

/**
 * 策略实现类
 */
public class NoFly implements Flyable{
    @Override
    public void fly() {
        System.out.println("Cannot fly");
    }
}
