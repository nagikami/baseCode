package nagi.java.designpattern.factory.simple;

public class SimpleFactory {
    public static Pizza createPizza(String type) throws Exception {
        Pizza pizza = null;

        switch (type) {
            case "A":
                pizza = new PizzaA("AA");
                break;
            case "B":
                pizza = new PizzaB("BB");
        }
        if (pizza != null) {
            pizza.prepare();
        } else {
            throw new Exception("Pizza " + type + " not exist");
        }
        return pizza;
    }
}
