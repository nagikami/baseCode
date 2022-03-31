package nagi.java.designpattern.command;

public class LightOffCommand implements Command {

    //聚合接受者
    private LightReceiver receiver;

    public LightOffCommand(LightReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.off();
    }

    @Override
    public void undo() {
        receiver.on();
    }
}
