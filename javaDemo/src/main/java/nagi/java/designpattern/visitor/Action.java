package nagi.java.designpattern.visitor;

//抽象访问者
public abstract class Action {
    //动态指定被访问者（person）的操作（getResult）
    abstract void getResult(Person person);
}
