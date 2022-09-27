package com.nagi.springbootdemo.aspectj;

import com.nagi.springbootdemo.SpringBootDemoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@SpringBootTest
public class TestAop implements ApplicationContextAware {

    private User user;

    @Test
    public void testAop() {
        user.add();
    }

    @Test
    public void testAopWithContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringBootDemoApplication.class);
        User user = context.getBean("user", User.class);
        user.add();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        user = applicationContext.getBean("user", User.class);
    }

    @Test
    public void testSpel() {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        Expression expression = spelExpressionParser.parseExpression("10 * ( 2 - 1)");
        System.out.println(expression.getValue());
    }
}
