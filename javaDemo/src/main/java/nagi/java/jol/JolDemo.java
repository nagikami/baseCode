package nagi.java.jol;

import org.openjdk.jol.info.ClassLayout;

public class JolDemo {
    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(String.class).toPrintable());
    }
}
