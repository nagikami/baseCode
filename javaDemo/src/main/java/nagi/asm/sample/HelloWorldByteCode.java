package nagi.asm.sample;

import org.junit.Test;

/**
 * 泛型T的类型描述符保存在类的signature属性里
 */
public class HelloWorldByteCode<T> {
    public static final int value = 10;

    // 注解Test的类型描述符保存在方法的RuntimeVisibleAnnotations属性的annotation属性里
    @Test
    // 泛型T的类型描述符保存在方法的signature属性里
    public <T> void test(T a) {
        // 使用new新建实例时，需要dup，因为调用<init>需要弹出一个对象引用，
        // 保存到局部变量表也要弹出一个对象引用
        HelloWorldByteCode helloWorld = new HelloWorldByteCode();
        System.out.println("Test method");
    }
}
