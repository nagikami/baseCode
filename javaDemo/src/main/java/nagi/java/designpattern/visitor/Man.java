package nagi.java.designpattern.visitor;

public class Man extends Person {

    public Man(String name) {
        this.name = name;
    }

    @Override
    void accept(Action action) {
        //返回自身对象给访问者
        action.getResult(this);
    }
}
