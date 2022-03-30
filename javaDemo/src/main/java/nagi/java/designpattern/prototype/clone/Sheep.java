package nagi.java.designpattern.prototype.clone;

//原型模式 实现Cloneable接口告诉jvm该对象可以克隆
//浅拷贝 值拷贝基本数据类型
public class Sheep implements Cloneable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return name;
    }
}
