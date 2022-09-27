package com.nagi.springbootdemo.reactor8;

import java.util.Observable;

public class ObserverDemo extends Observable {
    public static void main(String[] args) {
        ObserverDemo observer = new ObserverDemo();

        observer.addObserver((o, arg) -> {
            System.out.println("status changed");
        });

        observer.addObserver((o, arg) -> {
            System.out.println("status prepare to change");
        });

        observer.setChanged();
        observer.notifyObservers();
    }
}
