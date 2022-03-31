package nagi.java.designpattern.facade;

//子系统
public class DVDPlayer {
    private static DVDPlayer instance = new DVDPlayer();

    public static DVDPlayer getInstance() {
        return instance;
    }

    public void open() {
        System.out.println("DVDPlayer open");
    }

    public void close() {
        System.out.println("DVDPlayer close");
    }
}
