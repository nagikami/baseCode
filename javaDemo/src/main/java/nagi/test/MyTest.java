package nagi.test;

public class MyTest {
    public static void main(String[] args) {

    }
}

class Fruit {}

/**
 * code中泛型信息擦除，声明的泛型信息保存在signature属性中
 * @param <T>
 */
class FruitContainer<T extends Fruit> {
    T t;

    void add() {
        System.out.println(t);
    }
}
