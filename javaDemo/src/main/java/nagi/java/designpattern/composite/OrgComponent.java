package nagi.java.designpattern.composite;

//组件抽象类
public abstract class OrgComponent {
    private String name;
    private String des;

    public OrgComponent(String name, String des) {
        this.name = name;
        this.des = des;
    }

    //默认实现，子类选择性重写
    protected void add(OrgComponent orgComponent){}

    protected void remove(OrgComponent orgComponent){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    protected abstract void print();
}
