package nagi.java.designpattern.state;

public class  State1 extends State{

    private Activity activity;

    public State1(Activity activity) {
        this.activity = activity;
    }

    @Override
    void operation1() {
        System.out.println("Operation of State1");
        //设置活动为改变后的状态
        activity.setState(activity.getState2());
    }

    @Override
    void operation2() {
        System.out.println("cannot do operation2 in current state");
    }
}
