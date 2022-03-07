package com.nagi.springbootdemo.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component //增强类
@Aspect //生成代理对象
@Order(1) //设置增强类优先级
public class UserProxy {

    //相同切入点抽取
    @Pointcut(value = "execution(* com.nagi.springbootdemo.aspectj.User.add(..))")
    public void pointDemo() {

    }

    //前置通知
    @Before(value = "pointDemo()")
    public void before() {
        System.out.println("before...");
    }

    //最终通知（有无异常都执行）
    @After(value = "execution(* com.nagi.springbootdemo.aspectj.User.add(..))")
    public void after() {
        System.out.println("after...");
    }

    //方法返回后执行（有异常不执行）
    @AfterReturning(value = "execution(* com.nagi.springbootdemo.aspectj.User.add(..))")
    public void afterReturning() {
        System.out.println("after returning");
    }

    //异常通知
    @AfterThrowing(value = "execution(* com.nagi.springbootdemo.aspectj.User.add(..))")
    public void throwing() {
        System.out.println("throwing...");
    }

    //环绕通知
    @Around(value = "execution(* com.nagi.springbootdemo.aspectj.User.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("round before...");

        proceedingJoinPoint.proceed();

        System.out.println("round after...");

    }
}
