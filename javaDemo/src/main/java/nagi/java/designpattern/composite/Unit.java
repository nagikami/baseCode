package nagi.java.designpattern.composite;

//叶子节点
public class Unit extends OrgComponent {
    public Unit(String name, String des) {
        super(name, des);
    }

    @Override
    protected void print() {
        System.out.println(getName());
    }
}
