package nagi.java.designpattern.iterator;

import java.util.Iterator;

//聚合类
public class MyCollege implements College {

    private Department[] departments;
    //保存当前数组对象个数
    private int num = 0;

    public MyCollege() {
        departments = new Department[5];
    }

    @Override
    public String getName() {
        return "myCollege";
    }

    @Override
    public void addDepartment(String name, String des) {
        if (num < departments.length - 1) {
            departments[num] = new Department(name, des);
            num++;
        }
    }

    @Override
    public Iterator<Department> iterator() {
        return new DepartmentIterator(departments);
    }
}
