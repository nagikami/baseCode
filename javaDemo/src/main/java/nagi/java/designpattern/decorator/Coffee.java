package nagi.java.designpattern.decorator;

//被装饰者具体类
public class Coffee extends Drink {

    public Coffee() {
        setDes("coffee");
        setPrice(10);
    }

    @Override
    public double cost() {
        return price;
    }
}
