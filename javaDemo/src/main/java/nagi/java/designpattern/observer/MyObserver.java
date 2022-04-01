package nagi.java.designpattern.observer;

//观察者
public class MyObserver implements Observer{

    private String name;

    @Override
    public void update(String name) {
        this.name = name;
        display();
    }

    public void display() {
        System.out.println(this.name);
    }
}
