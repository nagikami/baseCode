package nagi.java.jdksrc;

public class Builder {
    public static void main(String[] args) {
        /**
         * Appendable为抽象构造器
         * AbstractStringBuilder为具体的构造器（不能实例化）
         * StringBuilder同时为指挥者和构造器（可实例化，具体的构造方法由AbstractStringBuilder为具体的构造器实现）
         */
        StringBuilder stringBuilder = new StringBuilder();
    }
}
