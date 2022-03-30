package nagi.java.designpattern.adapter.interfaceadapter;

/**
 * 接口适配器模式
 * 抽象类缺省实现dst接口 匿名类创建时实现部分dst接口，其余方法缺省（方法体为空）
 */
public abstract class AbsAdapter implements Interface2 {
    @Override
    public void m1() {

    }

    @Override
    public void m2() {

    }
}
