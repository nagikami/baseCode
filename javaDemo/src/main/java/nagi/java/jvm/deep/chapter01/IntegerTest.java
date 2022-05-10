package nagi.java.jvm.deep.chapter01;

public class IntegerTest {
    public static void main(String[] args) {
        Integer x = 5;
        int y = 5;
        System.out.println(x == y);

        Integer a = 5;
        Integer b = 5;
        System.out.println(a == b);

        Integer c = 128;
        Integer d = 128;
        System.out.println(c == d);
    }
}
