package nagi.java.designpattern.visitor;

public class SuccessAction extends Action {
    @Override
    void getResult(Person person) {
        System.out.println(person.name + " vote success");
    }
}
