package nagi.java.designpattern.memento;

public class Originator {
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    //保存状态，返回状态对象
    public Memento saveMemento() {
        return new Memento(state);
    }

    //从memento恢复状态
    public void getStateFromMemento(Memento memento) {
        this.state = memento.getState();
    }
}
