package nagi.java.jdksrc;

import java.util.HashMap;
import java.util.Map;

public class Composite {
    public static void main(String[] args) {
        /**
         * Map = Component抽象构建
         * HashMap 中间构建（composite） 实现/继承了相关方法put putAll
         * Node HashMap的静态内部类，叶子节点 没有put相关方法
         */
        Map<String, String> map = new HashMap<>();
    }
}
