package nagi.java.concurrent.lock;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class UnsafeArrayList {
    public static void main(String[] args) {
        //ArrayList线程不安全
        //unsafe();
        //Vector线程安全 1的方法 不推荐
        //safe();
        //Collections工具类 不推荐
        //safeC();
        //copy on write 写时复制 推荐
        safeW();
    }

    public static void unsafe() {
        List<String> strings = new ArrayList<>();

        for (int i = 0; i < 300; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString());
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }
    }

    public static void safe() {
        List<String> strings = new Vector<>();

        for (int i = 0; i < 300; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString());
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }
    }

    public static void safeC() {
        List<String> strings = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < 300; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString());
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }
    }

    public static void safeW() {
        List<String> strings = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 300; i++) {
            new Thread(() -> {
                strings.add(UUID.randomUUID().toString());
                System.out.println(strings);
            }, String.valueOf(i)).start();
        }
    }
}
