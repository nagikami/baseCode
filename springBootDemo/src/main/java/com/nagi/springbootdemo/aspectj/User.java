package com.nagi.springbootdemo.aspectj;

import org.springframework.stereotype.Component;

@Component //被代理类
public class User {
    public void add() {
        System.out.println("add...");
    }
}
