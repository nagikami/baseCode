package nagi.java.designpattern.iterator;

import java.util.Iterator;

//聚合接口
public interface College {
    String getName();
    void addDepartment(String name, String des);
    Iterator iterator();
}
