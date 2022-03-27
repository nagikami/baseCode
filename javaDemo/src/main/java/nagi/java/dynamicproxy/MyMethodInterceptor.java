package nagi.java.dynamicproxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class MyMethodInterceptor implements MethodInterceptor {
    //method代理方法，使用反射通过方法名调用（慢），methodProxy代理方法通过FastClass以索引方式调用（快）
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("before method");
        //invokeSuper调用代理方法支持自身增强，invoke调用被代理方法
        Object res =  methodProxy.invokeSuper(o, objects);
        System.out.println("after method");
        return res;
    }
}
