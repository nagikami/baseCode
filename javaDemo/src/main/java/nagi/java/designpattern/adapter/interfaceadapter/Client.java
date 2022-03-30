package nagi.java.designpattern.adapter.interfaceadapter;

/**
 * adapter适配器模式，又称wrapper封装模式
 */
public class Client {
    public static void main(String[] args) {
        new AbsAdapter() {
            //只覆盖使用的方法
            @Override
            public void m1() {
                System.out.println("only use m1");
            }
        }.m1();
    }
}
