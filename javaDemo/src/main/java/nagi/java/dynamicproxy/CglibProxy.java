package nagi.java.dynamicproxy;

import net.sf.cglib.proxy.Enhancer;

//使用ASM，修改被代理类字节码文件，返回增强代理类的byte[]
public class CglibProxy {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDaoImpl.class);
        enhancer.setCallback(new MyMethodInterceptor());
        UserDaoImpl userDaoImplProxy = (UserDaoImpl) enhancer.create();
        System.out.println(userDaoImplProxy.multiply(1, 2));
    }
}
