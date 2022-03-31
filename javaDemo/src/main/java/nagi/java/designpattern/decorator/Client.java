package nagi.java.designpattern.decorator;

/**
 * 使用decorator对component（组件）进行装饰（装饰和被装饰者都是组件抽象的子类）
 * Decorator继承组件的抽象（component）同时进行组合
 */
public class Client {
    public static void main(String[] args) {
        Coffee coffee = new Coffee();
        Component bread = new Bread(coffee);
        Component bread1 = new Bread(bread);
        System.out.println(bread.cost());
        System.out.println(bread1.cost());
    }
}
