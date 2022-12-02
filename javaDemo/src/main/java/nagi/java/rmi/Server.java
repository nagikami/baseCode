package nagi.java.rmi;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * 服务端启动类，创建远程对象注册表并注册远程对象
 */
public class Server {
    public static void main(String[] args) {
        try {
            // 创建远程对象注册表Registry实例，Java默认端口1099
            LocateRegistry.createRegistry(8099);
            // 创建远程对象
            UserOperating userOperating = new UserOperatingImpl();
            // 注册远程对象到注册表，命名为userOperating，name也可以省略协议名（rmi:）
            Naming.bind("rmi://localhost:8099/userOperating", userOperating);
            System.out.println("remote object register successfully!");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
