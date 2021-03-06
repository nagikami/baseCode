package nagi.java.jvm.profiler.chapter02;

import java.util.ArrayList;

/**
 * -Xms60m -Xmx60m -XX:SurvivorRatio=8
 * -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=E:\testData\m.hprof OOM时自动导出堆转储文件
 */
public class GCTest {
    public static void main(String[] args) {
        ArrayList<byte[]> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            byte[] bytes = new byte[100 * 1024];
            list.add(bytes);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
