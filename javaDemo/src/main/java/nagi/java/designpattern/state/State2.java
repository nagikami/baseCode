package nagi.java.designpattern.state;

public class State2 extends State {

    private Activity activity;

    public State2(Activity activity) {
        this.activity = activity;
    }

    @Override
    void operation1() {
        System.out.println("cannot do operation1 in current state");
    }

    @Override
    void operation2() {
        System.out.println("Operation of State2");
        activity.setState(activity.getState1());
    }
}
