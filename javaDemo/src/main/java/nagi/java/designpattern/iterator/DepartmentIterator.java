package nagi.java.designpattern.iterator;

import java.util.Iterator;

//基于数组的迭代器
public class DepartmentIterator implements Iterator<Department> {

    //保存departments
    Department[] departments;
    //遍历位置
    int position = 0;

    public DepartmentIterator(Department[] departments) {
        this.departments = departments;
    }

    @Override
    public boolean hasNext() {
        if (position >= departments.length || departments[position] == null) {
            return false;
        }
        return true;
    }

    @Override
    public Department next() {
        return departments[position++];
    }

    @Override
    public void remove() {

    }
}
