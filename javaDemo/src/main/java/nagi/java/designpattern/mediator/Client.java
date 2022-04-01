package nagi.java.designpattern.mediator;

/**
 * 中介者模式
 * 用一个中介对象封装一系列对象交互
 * Mediator 抽象中介者，定义同事对象到中介者接口
 * Colleague 抽象同事类
 * ConcreteMediator 具体中介者对象，聚合所有的同事类，用集合管理，并接收某个对象消息完成相应任务
 * ConcreteColleague 具体同事类，每个同事类行为独立不知道其他同事行为，都依赖中介者对象
 */
public class Client {
    public static void main(String[] args) {
        Mediator mediator = new ConcreteMediator();
        //注册Colleague到中介者
        Alarm a = new Alarm(mediator, "a");
        CoffeeMachine b = new CoffeeMachine(mediator, "b");
        a.senMessage(0);
    }
}
