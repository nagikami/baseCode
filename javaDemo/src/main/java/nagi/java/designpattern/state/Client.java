package nagi.java.designpattern.state;

/**
 * 状态模式
 * 对象在状态改变时，对外输出不同的行为
 * Context 环境角色，用于维护State实例，这个实例定义当前状态
 * State 抽象状态角色，定义一个接口封装与Context的一个特定接口的相关行为
 * ConcreteState 具体状态角色，每个子类实现一个与Context的状态相关行为
 */
public class Client {
    public static void main(String[] args) {
        Activity activity = new Activity();
        activity.getState().operation1();
        activity.getState().operation1();
        activity.getState().operation2();
    }
}
