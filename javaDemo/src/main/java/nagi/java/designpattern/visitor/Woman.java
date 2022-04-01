package nagi.java.designpattern.visitor;

public class Woman extends Person {

    public Woman(String name) {
        this.name = name;
    }

    @Override
    void accept(Action action) {
        action.getResult(this);
    }
}
