package nagi.java.designpattern.iterator;

import java.util.Iterator;

/**
 * Iterator 迭代器接口，系统提供，hasNext next remove
 * ConcreteIterator 具体迭代器类，管理迭代
 * Aggregate 聚合接口，解耦客户端和迭代器
 * ConcreteAggregate 具体聚合持有对象集合，提供方法返回一个迭代器
 */
public class Client {
    public static void main(String[] args) {
        MyCollege myCollege = new MyCollege();
        myCollege.addDepartment("a", "b");
        myCollege.addDepartment("b", "c");
        Iterator<Department> iterator = myCollege.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getName());
        }
    }
}
