package nagi.java.designpattern.facade;

public class Popcorn {
    private static Popcorn instance = new Popcorn();

    public static Popcorn getInstance() {
        return instance;
    }

    public void open() {
        System.out.println("Popcorn open");
    }

    public void close() {
        System.out.println("Popcorn close");
    }
}
