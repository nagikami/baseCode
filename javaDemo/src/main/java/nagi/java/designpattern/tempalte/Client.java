package nagi.java.designpattern.tempalte;

/**
 * 模板模式
 * template定义算法的大致流程 实现可以放在子类
 * AbstractClass抽象类，类中实现了模板方法（template）定义了算法骨架
 * ConcreteClass实现抽象方法
 * Spring IOC 容器初始化的 ConfigurableApplicationContext 使用模板模式
 */
public class Client {
    public static void main(String[] args) {
        new MyMilk().make();
    }
}
