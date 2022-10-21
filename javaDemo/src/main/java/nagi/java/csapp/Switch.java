package nagi.java.csapp;

public class Switch {
    public static void main(String[] args) {
        System.out.println(1);
    }

    /**
     * 源码
     * switch通过跳转表（jump table）实现，跳转表是一个存储代码段地址（运算符&&表示创建代码段的指针）的数组
     * 使用跳转表需要a的值连续
     * @param a
     */
    private static void source(int a) {
        switch (a) {
            case 1:
                System.out.println("hello a");
                break;
            case 2:
                System.out.println("hello b");
                break;
            case 3:
                System.out.println("hello c");
                break;
            default:
                System.out.println("alpha");
        }
    }
}
