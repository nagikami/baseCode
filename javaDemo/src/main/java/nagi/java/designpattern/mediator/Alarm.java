package nagi.java.designpattern.mediator;

//具体同事类
public class Alarm extends Colleague {


    public Alarm(Mediator mediator, String name) {
        super(mediator, name);
        //同事类自动注册到中介者
        mediator.register(name, this);
    }

    public void sendAlarm(int stateChange) {
        senMessage(stateChange);
    }

    @Override
    public void senMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }
}
