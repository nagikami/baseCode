package nagi.java.designpattern.visitor;

/**
 * 访问者模式
 * 分离数据结构和操作
 * Visitor 抽象访问者，为子类ConcreteElement声明一个visit操作
 * ConcreteVisitor 具体访问者，实现操作
 * ObjectStructure 枚举元素，提供高层接口允许访问者访问元素
 * Element 定义accept方法，接收访问者
 * ConcreteElement 具体元素，实现accept方法
 * 把元素属性剥离至访问者，动态扩展访问者
 */
public class Client {
    public static void main(String[] args) {
        ObjectStructure objectStructure = new ObjectStructure();

        Man tom = new Man("tom");
        Woman jenny = new Woman("jenny");

        objectStructure.attach(tom);
        objectStructure.attach(jenny);

        SuccessAction successAction = new SuccessAction();
        //为元素集合动态添加getResult操作，解耦属性和操作
        objectStructure.getAllResult(successAction);
    }
}
