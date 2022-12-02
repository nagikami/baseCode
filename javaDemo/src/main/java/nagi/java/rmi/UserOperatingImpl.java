package nagi.java.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 远程接口实现类，需要继承UnicastRemoteObject、
 * 该类的对象会作为远程对象注册到注册表，在服务端由Skeleton（骨架）代理，负责和客户端的远程对象的存根对象（stub）
 * 进行通信
 */
public class UserOperatingImpl extends UnicastRemoteObject implements UserOperating {
    protected UserOperatingImpl() throws RemoteException {

    }

    @Override
    public User updateName(User user) throws RemoteException {
        user.setName("nagisama");
        return user;
    }
}
