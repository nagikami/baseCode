package com.nagi.test;

public class TestCode {
    public static void main(String[] args) {
        //单调时钟统计持续时间
        long startTime = System.nanoTime();
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            count++;
        }
        long endTime = System.nanoTime();
        System.out.println(count + ": " + (endTime - startTime));
    }
}
