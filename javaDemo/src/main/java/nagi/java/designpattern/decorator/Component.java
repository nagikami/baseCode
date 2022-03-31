package nagi.java.designpattern.decorator;

//定义组件抽象
public abstract class Component {
    protected double price;
    protected String des;

    public abstract double cost();

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
