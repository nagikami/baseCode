package nagi.java.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * 客户端类，访问注册表获取远程对象的存根对象（stub），实现远程方法调用
 */
public class Client {
    public static void main(String[] args) {
        try {
            /**
             * 获取远程对象的存根对象stub
             * stub对象包含远程对象在服务端的引用，基于远程接口使用JDK动态代理创建，远程方法在stub中的方法体为和
             * skeleton通信并获取返回结果的逻辑
             * stub将序列化的Method（远程方法签名）和参数对象以及远程对象的引用发送给skeleton，skeleton通过调用
             * 注册到registry的远程方法实例使用反射调用对应的方法
             */
            UserOperating userOperating = (UserOperating) Naming.lookup("rmi://localhost:8099/userOperating");
            User user = new User("nagi", 18);
            // 调用远程方法
            User result = userOperating.updateName(user);
            System.out.println("User response from server, name: " + result.getName() + " age: " + result.getAge());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
