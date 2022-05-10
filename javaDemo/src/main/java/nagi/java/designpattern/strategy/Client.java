package nagi.java.designpattern.strategy;

/**
 * 策略模式
 * 定义算法族，分别分装起来，让他们之间可以相互替换，让算法的变化独立于算法的客户
 * Context 上下文，聚合或者组合多个策略
 * Strategy 策略接口
 * ConcreteStrategy 具体的策略实现类
 */
public class Client {
    public static void main(String[] args) {
        WildDuck wildDuck = new WildDuck();
        wildDuck.fly();

        ToyDuck toyDuck = new ToyDuck();
        toyDuck.fly();

        wildDuck.setFlyable(new NoFly());
        wildDuck.fly();
    }
}
