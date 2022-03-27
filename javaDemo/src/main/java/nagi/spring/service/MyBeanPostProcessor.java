package nagi.spring.service;

import nagi.spring.spring.BeanPostProcessor;
import nagi.spring.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@Component //扫描时记录
public class MyBeanPostProcessor implements BeanPostProcessor {
    //在所有bean初始化前调用，可以通过PostConstruct注解声明单个bean的初始化前方法
    @Override
    public Object postProcessBeforeInitialization(String beanName, Object bean) {
        if ("userService".equals(beanName)) {
            System.out.println("Before bean initialization");
        }
        return bean;
    }

    //在所有bean初始化后调用，可以通过实现FactoryBean接口创建单个bean的代理
    @Override
    public Object postProcessAfterInitialization(String beanName, Object bean) {
        if ("userService".equals(beanName)) {
            System.out.println("After bean initialization");
            //为该bean创建代理对象
            Object proxyInstance = Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader(), UserService.class.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("执行切面逻辑");
                    //匿名内部类引用外部类变量，生成字节码文件时有参final构造，避免外部类变化修改内部类运行中的值
                    return method.invoke(bean, args);
                }
            });
            return proxyInstance;
        }
        return bean;
    }
}
