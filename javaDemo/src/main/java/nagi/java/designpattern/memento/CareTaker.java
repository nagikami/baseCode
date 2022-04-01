package nagi.java.designpattern.memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    //保存memento对象
    private List<Memento> mementoList = new ArrayList<>();

    public void add(Memento memento) {
        mementoList.add(memento);
    }

    public Memento get(int index) {
        return mementoList.get(index);
    }
}
