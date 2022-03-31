package nagi.java.designpattern.command;

public class LightOnCommand implements Command {

    //聚合接受者
    private LightReceiver receiver;

    public LightOnCommand(LightReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.on();
    }

    @Override
    public void undo() {
        receiver.off();
    }
}
