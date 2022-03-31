package nagi.java.designpattern.command;

//没有任何命令，空指令用于初始化 可以省略空判断
public class NoCommand implements Command{

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
