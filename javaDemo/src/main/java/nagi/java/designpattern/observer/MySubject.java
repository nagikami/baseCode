package nagi.java.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

//数据持有者
public class MySubject implements Subject{

    private String name;

    //观察者列表
    List<Observer> observers = new ArrayList<>();

    //数据更新通知所有注册的观察者
    public void updateName(String name) {
        this.name = name;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this.name);
        }
    }
}
