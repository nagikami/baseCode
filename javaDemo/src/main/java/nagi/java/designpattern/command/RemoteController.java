package nagi.java.designpattern.command;

//命令发送者
public class RemoteController {
    //开关按钮数组
    private Command[] onCommands;
    private Command[] offCommands;

    //执行撤销的指令
    private Command undoCommand;

    public RemoteController() {
        onCommands = new Command[5];
        offCommands = new Command[5];
        for (int i = 0; i < 5; i++) {
            onCommands[i] = new NoCommand();
            onCommands[i] = new NoCommand();
        }
    }

    //给按钮设置命令
    public void setCommand(int no, Command onCommand, Command offCommand) {
        onCommands[no] = onCommand;
        offCommands[no] = offCommand;
    }

    //按下开按钮
    public void onButtonWasPushed(int no) {
        onCommands[no].execute();
        //记录当前命令，用于撤销
        undoCommand = onCommands[no];
    }

    public void offButtonWasPushed(int no) {
        offCommands[no].execute();
        undoCommand = offCommands[no];
    }

    //执行撤销
    public void undoButtonWasPushed() {
        undoCommand.undo();
    }
}
