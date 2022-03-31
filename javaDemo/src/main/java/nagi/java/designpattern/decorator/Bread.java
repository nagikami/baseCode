package nagi.java.designpattern.decorator;

//装饰者类
public class Bread extends Decorator {
    public Bread(Component component) {
        super(component);
        setDes("bread");
        setPrice(2);
    }
}
