package nagi.java.designpattern.prototype.deepclone;

public class Client {
    public static void main(String[] args) {
        Parent parent = new Parent();
        parent.child = new Child();
        try {
            Parent clone = (Parent) parent.clone();
            System.out.println(parent.child.hashCode());
            System.out.println(clone.child.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Parent deepClone = (Parent) parent.deepClone();
        System.out.println(deepClone.child.hashCode());
    }
}
