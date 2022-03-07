package nagi.java.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class JDKProxy {
    public static void main(String[] args) {
        UserDaoImpl userDao = new UserDaoImpl();

        UserDao userDaoProxy = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), new Class[]{UserDao.class}, new UserDaoProxy(userDao));
        System.out.println(userDaoProxy.add(1, 2));
    }

    static class UserDaoProxy implements InvocationHandler {

        private Object object;

        //传入被代理对象实例
        public UserDaoProxy(Object object) {
            this.object = object;
        }

        //增强逻辑
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("Before method...");
            System.out.println("Method name is " + method.getName() + " args are " + Arrays.toString(args));

            Object result = method.invoke(object, args);

            System.out.println("After method...");

            return result;
        }
    }
 }
