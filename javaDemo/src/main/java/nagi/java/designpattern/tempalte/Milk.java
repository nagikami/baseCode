package nagi.java.designpattern.tempalte;

public abstract class Milk {
    //模板方法可以定义为final，避免被子类所覆盖
    final void make() {
        select();
        if (isBoiled()) {
            boil();
        }
    }

    abstract void select();

    abstract void boil();

    //钩子方法，默认无需重写
    boolean isBoiled() {
        return true;
    }
}
