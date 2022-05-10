package nagi.java.jvm.deep.chapter01;

public class StringTest {
    public static void main(String[] args) {
        String str = new String("Hello") + new String("World");
        String str1 = "HelloWorld";
        System.out.println(str == str1);
    }
}
