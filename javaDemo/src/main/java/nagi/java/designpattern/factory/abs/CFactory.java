package nagi.java.designpattern.factory.abs;

//实现抽象工厂接口
public class CFactory implements AbsFactory {
    @Override
    public void createPizza(String type) {
        System.out.println("create pizza of C");
        Pizza pizza = null;
        switch (type) {
            case "A":
                pizza = new CPizzaA("CAA");
                break;
            case "B":
                pizza = new CPizzaB("CBB");
        }
        if (pizza != null) {
            pizza.prepare();
        }
    }
}
