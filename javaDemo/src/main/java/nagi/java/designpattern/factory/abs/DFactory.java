package nagi.java.designpattern.factory.abs;

public class DFactory implements AbsFactory {
    @Override
    public void createPizza(String type) {
        System.out.println("create pizza of D");
        Pizza pizza  = null;
        switch (type) {
            case "A":
                pizza = new DPizzaA("DAA");
                break;
            case "B":
                pizza = new DPizzaB("DBB");
        }
        if (pizza != null) {
            pizza.prepare();
        }
    }
}
