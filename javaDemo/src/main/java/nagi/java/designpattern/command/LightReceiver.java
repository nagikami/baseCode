package nagi.java.designpattern.command;

/**
 * 命令执行者
 */
public class LightReceiver {
    public void on() {
        System.out.println("Light is on");
    }

    public void off() {
        System.out.println("Light is off");
    }
}
