package nagi.java.designpattern.mediator;

public class CoffeeMachine extends Colleague {
    public CoffeeMachine(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void startCoffee() {
        System.out.println("start coffee");
    }

    @Override
    public void senMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.name);
    }
}
