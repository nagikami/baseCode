package nagi.java.designpattern.builder;

//抽象构造器
public abstract class AbstractBuilder {
    protected House house = new House();

    //抽象构造实例方法
    public abstract void crateBasic();
    public abstract void createWall();

    //返回建造好的实例
    public House buildHouse() {
        return house;
    }
}
