package nagi.java.designpattern.state;

public class Activity {
    private State state;

    private State state1 = new State1(this);
    private State state2 = new State2(this);

    public Activity() {
        state = state1;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState1() {
        return state1;
    }

    public State getState2() {
        return state2;
    }
}
