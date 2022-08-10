package nagi.java.jmx;

/**
 * 具体的MBean，实现接口声明的资源操作
 */
public class ServerInfo implements ServerInfoMBean {

    String state = "Running";

    @Override
    public String getCurrentState() {
        return state;
    }

    @Override
    public void printCurrentState() {
        System.out.println("Invocation from client, state: " + state);
    }

    @Override
    public void changeState(String state) {
        this.state = state;
    }
}
