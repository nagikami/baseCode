package nagi.spring.service;

import nagi.spring.spring.MyApplicationContext;

public class Test {
    public static void main(String[] args) {
        MyApplicationContext myApplicationContext = new MyApplicationContext(MyConfig.class);
        MyService myService = (MyService)myApplicationContext.getBean("myService");
    }
}
