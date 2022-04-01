package nagi.java.designpattern.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理所有元素对象
 */
public class ObjectStructure {
    //维护元素集合
    private List<Person> people = new ArrayList<>();

    //添加元素
    public void attach(Person person) {
        people.add(person);
    }

    //移除元素
    public void detach(Person person) {
        people.add(person);
    }

    //为元素添加访问者，添加动态属性
    public void getAllResult(Action action) {
        for (Person person : people) {
            person.accept(action);
        }
    }
}
