package nagi.java.designpattern.command;

//命令接口
public interface Command {
    //执行命令
    void execute();
    //撤销命令
    void undo();
}
