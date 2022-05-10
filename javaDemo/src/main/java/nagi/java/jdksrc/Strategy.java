package nagi.java.jdksrc;

import java.util.Arrays;
import java.util.Comparator;

public class Strategy {
    public static void main(String[] args) {
        Integer[] ints = {1, 6, 3, 8};

        /**
         * 实现了Comparator接口（策略接口），匿名类对象 new Comparator<Integer>(){...}就是策略接口实现类实例
         */
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return 1;
                } else if (o1 < o2) {
                    return -1;
                }
                return 0;
            }
        };

        Arrays.sort(ints, comparator);
        System.out.println(Arrays.toString(ints));

        //支持函数接口@FunctionalInterface，可以使用lambda表达式
        Arrays.sort(ints, (var1, var2) -> {
            if (var1 > var2) {
                return -1;
            } else if (var1 < var2) {
                return 1;
            } else {
                return 0;
            }
        });
        System.out.println(Arrays.toString(ints));
    }
}
