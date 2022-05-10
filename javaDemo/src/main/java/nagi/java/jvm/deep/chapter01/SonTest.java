package nagi.java.jvm.deep.chapter01;

/**
 * 成员变量（非静态）赋值过程：默认初始化、父类构造器初始化、显示初始化/代码块初始化、构造器初始化、有了对象使用属性或方法赋值
 */
class Father {
    int x = 10;

    public Father() {
        this.print();
        this.x = 20;
    }

    public void print() {
        System.out.println("Father.x = " + x);
    }
}

class Son extends Father {
    int x = 30;

    public Son() {
        this.print();
        this.x = 40;
    }

    public void print() {
        System.out.println("Son.x = " + x);
    }
}

public class SonTest {
    public static void main(String[] args) {
        Father father = new Son();
        System.out.println(father.x);
    }
}
