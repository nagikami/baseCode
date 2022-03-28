package nagi.java.designpattern.singleton;

public class LazyEnum {
    public static void main(String[] args) {
        Singleton6 instance = Singleton6.INSTANCE;
        Singleton6 instance1 = Singleton6.INSTANCE;
        System.out.println(instance == instance1);
    }
}

//懒汉式（枚举）推荐
//防止反序列化创建对象
enum Singleton6 {
    INSTANCE;
    public void sayOk() {
        System.out.println("ok");
    }
}