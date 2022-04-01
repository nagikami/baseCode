package nagi.java.designpattern.visitor;

//元素抽象类
public abstract class Person {

    protected String name;

    //接收访问者
    abstract void accept(Action action);
}
