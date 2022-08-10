package nagi.java.jmx;

/**
 * 定义资源管理/监控接口，声明可以对资源进行哪些操作，必须以MBean结尾
 */
public interface ServerInfoMBean {
    String getCurrentState();
    void printCurrentState();
    void changeState(String state);
}
