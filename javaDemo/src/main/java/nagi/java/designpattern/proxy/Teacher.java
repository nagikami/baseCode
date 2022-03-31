package nagi.java.designpattern.proxy;

public class Teacher implements ITeacher {
    @Override
    public void teach() {
        System.out.println("Teach starts");
    }
}
