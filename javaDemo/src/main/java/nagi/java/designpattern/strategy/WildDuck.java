package nagi.java.designpattern.strategy;

/**
 * 实体实现类
 */
public class WildDuck extends Duck{

    public WildDuck() {
        this.flyable = new CanFly();
    }

    @Override
    public void display() {
        System.out.println("wild duck");
    }
}
