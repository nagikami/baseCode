package nagi.java.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 远程接口，包含服务端暴露的远程方法表
 * 需要继承Remote接口，并且每个远程方法需要抛出RemoteException
 */
public interface UserOperating extends Remote {
    User updateName(User user) throws RemoteException;
}
