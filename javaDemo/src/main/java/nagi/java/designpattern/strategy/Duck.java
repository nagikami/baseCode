package nagi.java.designpattern.strategy;

/**
 * 抽象上下文
 */
public abstract class Duck {

    protected Flyable flyable;

    public abstract void display();

    public void swim() {
        System.out.println("Duck can swim");
    }

    public void fly() {
        if (flyable != null) {
            flyable.fly();
        }
    }

    public void setFlyable(Flyable flyable) {
        this.flyable = flyable;
    }
}
