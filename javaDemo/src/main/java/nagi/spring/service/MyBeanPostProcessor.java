package nagi.spring.service;

import nagi.spring.spring.BeanPostProcessor;
import nagi.spring.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component //扫描时记录
public class MyBeanPostProcessor implements BeanPostProcessor {
    //在所有bean初始化前调用
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if ("userService".equals(beanName)) {
            System.out.println("Before bean initialization");
        }
        return bean;
    }

    //在所有bean初始化后调用
    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if ("userService".equals(beanName)) {
            System.out.println("After bean initialization");
            //为该bean创建代理对象
            Object proxyInstance = Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader(), UserService.class.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("执行切面逻辑");
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
