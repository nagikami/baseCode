package nagi.java.designpattern.factory.simple;

//实例化时注入工厂依赖是组合，通过属性setter或者构造方法注入是聚合，当前为使用静态方法
public class Order {
    public void order(String type) {
        try {
            SimpleFactory.createPizza(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
