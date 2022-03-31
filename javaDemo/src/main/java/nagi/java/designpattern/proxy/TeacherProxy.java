package nagi.java.designpattern.proxy;

public class TeacherProxy implements ITeacher {

    private ITeacher target;

    public TeacherProxy(ITeacher target) {
        this.target = target;
    }

    @Override
    public void teach() {
        System.out.println("before teach");
        target.teach();
        System.out.println("after teach");
    }
}
