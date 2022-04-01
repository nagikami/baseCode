package nagi.java.designpattern.visitor;

public class FailAction extends Action {
    @Override
    void getResult(Person person) {
        System.out.println(person.name + " vote failed");
    }
}
