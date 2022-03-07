package com.nagi.springbootdemo.aspectj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SpringBootTest
public class TestAop implements ApplicationContextAware {

    private User user;

    @Test
    public void testAop() {
        user.add();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        user = applicationContext.getBean("user", User.class);
    }
}
