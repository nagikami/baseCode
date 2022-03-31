package nagi.java.designpattern.composite;

/**
 * 组合模式 树形结构展示
 * 定义组件抽象类 依据包含关系定义继承组件类的组合类
 */
public class Client {
    public static void main(String[] args) {
        College college = new College("aCollege", "a");
        Department department = new Department("aDepartment", "a");
        Department department1 = new Department("bDepartment", "b");
        college.add(department);
        college.add(department1);
        Unit unit = new Unit("aUnit", "a");
        Unit unit1 = new Unit("bUnit", "ab");
        department.add(unit);
        department1.add(unit1);
        college.print();
    }
}
