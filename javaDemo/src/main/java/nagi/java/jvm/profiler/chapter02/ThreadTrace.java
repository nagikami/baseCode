package nagi.java.jvm.profiler.chapter02;

import java.util.Map;
import java.util.Set;

public class ThreadTrace {
    public static void main(String[] args) {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces(); // 追踪当前进程中的所有的线程
        Set<Map.Entry<Thread, StackTraceElement[]>> entries = allStackTraces.entrySet();
        for (Map.Entry<Thread, StackTraceElement[]> entry : entries) {
            Thread thread = entry.getKey();
            System.out.println("Current Thread is " + thread.getName());
            StackTraceElement[] value = entry.getValue();
            for (StackTraceElement stackTraceElement : value) {
                System.out.println("\t" + stackTraceElement.toString());
            }
        }
    }
}
