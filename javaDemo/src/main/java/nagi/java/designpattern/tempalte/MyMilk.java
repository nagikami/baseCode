package nagi.java.designpattern.tempalte;

public class MyMilk extends Milk{
    @Override
    void select() {
        System.out.println("select...");
    }

    @Override
    void boil() {
        System.out.println("boil...");
    }
}
