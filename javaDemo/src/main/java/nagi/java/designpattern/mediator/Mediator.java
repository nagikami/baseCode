package nagi.java.designpattern.mediator;

public abstract class Mediator {
    //将同事对象加入到集合中
    abstract void register(String name, Colleague colleague);

    //接收具体同事发出的消息
    abstract void getMessage(int stateChange, String name);
}
