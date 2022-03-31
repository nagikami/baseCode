package nagi.java.designpattern.composite;

import java.util.ArrayList;
import java.util.List;

public class College extends OrgComponent{

    //聚合的组件（Department节点）
    List<OrgComponent> orgComponents = new ArrayList<>();

    public College(String name, String des) {
        super(name, des);
    }

    @Override
    protected void add(OrgComponent orgComponent) {
        orgComponents.add(orgComponent);
    }

    @Override
    protected void remove(OrgComponent orgComponent) {
       orgComponent.remove(orgComponent);
    }

    @Override
    protected void print() {
        System.out.println("------------" + getName() + "---------------");
        for (OrgComponent orgComponent : orgComponents) {
            orgComponent.print();
        }
    }
}
