package nagi.java.designpattern.memento;

/**
 * 备忘录模式
 * 捕获对象内部状态，保存在外部
 * Originator 需要保存状态的对象
 * Memento 备忘录对象，记录对象状态对象
 * CareTaker 守护者对象，负责保存多个备忘录对象
 * 游戏闯关，失败返回初始化状态
 * 为了节省资源，可以和原型模式配合使用
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("100");
        Memento memento = originator.saveMemento();
        CareTaker careTaker = new CareTaker();
        //添加状态至备忘录
        careTaker.add(memento);
        originator.setState("50");
        System.out.println(originator.getState());
        //从备忘录加载状态
        originator.getStateFromMemento(careTaker.get(0));
        System.out.println(originator.getState());
    }
}
