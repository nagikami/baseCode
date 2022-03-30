package nagi.java.designpattern.prototype.clone;

public class Client {
    public static void main(String[] args) {
        Sheep sheep = new Sheep();
        sheep.setName("nagi");
        try {
            Object clone = sheep.clone();
            System.out.println(clone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
