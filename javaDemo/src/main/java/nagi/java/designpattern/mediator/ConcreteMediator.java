package nagi.java.designpattern.mediator;

import java.util.HashMap;

public class ConcreteMediator extends Mediator {
    private HashMap<String, Colleague> colleagueHashMap;
    private HashMap<String, String> innerMap;

    public ConcreteMediator() {
        colleagueHashMap = new HashMap<>();
        innerMap = new HashMap<>();
    }

    @Override
    void register(String name, Colleague colleague) {
        colleagueHashMap.put(name, colleague);

        if (colleague instanceof Alarm) {
            innerMap.put("Alarm", name);
        } else if(colleague instanceof CoffeeMachine) {
            innerMap.put("CoffeeMachine", name);
        }
    }

    /**
     * 中介者核心方法
     * 根据得到的消息完成对应的任务 协调各个同事对象
     * @param stateChange
     * @param name
     */
    @Override
    void getMessage(int stateChange, String name) {
        if (colleagueHashMap.get(name) instanceof Alarm) {
            if(stateChange == 0) {
                CoffeeMachine coffeeMachine = (CoffeeMachine) colleagueHashMap.get(innerMap.get("CoffeeMachine"));
                coffeeMachine.startCoffee();
            }
        } else if (colleagueHashMap.get(name) instanceof CoffeeMachine) {

        }
    }
}
