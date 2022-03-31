package nagi.java.designpattern.command;

/**
 * 命令模式
 * 使请求发送者和接受者解耦 命令封装成一个对象 支持命令撤销
 * Invoker 调用者角色
 * Command 命令角色 封装需要执行的命令 接口或抽象类
 * Receiver 接收者角色 执行具体的操作
 * ConcreteCommand 绑定一个接受者和一个动作（Command），调用接受者操作实现execute
 * Spring的JdbcTemplate使用命令模式
 */
public class Client {
    public static void main(String[] args) {
        LightReceiver lightReceiver = new LightReceiver();
        //初始化命令
        LightOnCommand lightOnCommand = new LightOnCommand(lightReceiver);
        LightOffCommand lightOffCommand = new LightOffCommand(lightReceiver);
        //初始化遥控器
        RemoteController remoteController = new RemoteController();
        //绑定按钮与命令
        remoteController.setCommand(0, lightOnCommand, lightOffCommand);
        remoteController.onButtonWasPushed(0);
        remoteController.offButtonWasPushed(0);
        remoteController.undoButtonWasPushed();
    }
}
