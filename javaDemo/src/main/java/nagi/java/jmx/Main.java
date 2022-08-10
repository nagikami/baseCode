package nagi.java.jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;

/**
 * JMX(Java Management Extensions)，为应用程序提供资源监控、管理的框架
 * 设备层（Instrumentation Level）：定义资源MBean（Managed Bean）
 * 代理层（Agent Level）：核心为MBeanServer，管理注册到MBeanServer的资源，
 * 提供和协议适配器（Adapter）、连接器（Connector）通信的接口
 * 分布服务层（Distributed Service Level）：定义一系列接收客户端请求、和代理层通信的组件（Adapter、Connector）
 */
public class Main {
    public static void main(String[] args) throws Exception {
        // 获取MBeanServer
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        ServerInfo serverInfo = new ServerInfo();
        // 构建MBean名称标识对象
        ObjectName objectName = new ObjectName("serverInfoMBean:name=serverInfo");
        // 注册MBean
        platformMBeanServer.registerMBean(serverInfo, objectName);

        /**
         * 如果需要非本机的的客户端远程连接并管理应用，需要通过RMI server监听JMX服务的请求
         * 可以通过代码或者通过指定系统属性启动RMI server
         */
        // 开启RMI(Remote Method Invocation) server端口监听
        LocateRegistry.createRegistry(9999);
        // 声明JMX服务URL
        JMXServiceURL jmxServiceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
        // 创建connector（指定监听客户端请求的JMX服务URL和连接的MBeanServer）
        JMXConnectorServer jmxConnectorServer = JMXConnectorServerFactory.newJMXConnectorServer(jmxServiceURL, null, platformMBeanServer);
        jmxConnectorServer.start();

        while (true) {
            System.out.println("Server state tracking, current state: " + serverInfo.getCurrentState());
            Thread.sleep(10000);
        }
    }
}
