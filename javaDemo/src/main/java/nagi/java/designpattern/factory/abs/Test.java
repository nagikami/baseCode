package nagi.java.designpattern.factory.abs;

public class Test {
    public static void main(String[] args) {
        new Order().order(new DFactory(), "B");
    }
}
