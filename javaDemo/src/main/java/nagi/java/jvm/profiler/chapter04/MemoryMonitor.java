package nagi.java.jvm.profiler.chapter04;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class MemoryMonitor {
    public static void main(String[] args) {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("init heap: " + usage.getInit() / 1024 + "K");
        System.out.println("max heap: " + usage.getMax() / 1024 + "K");
        System.out.println("used heap: " + usage.getUsed() / 1024 + "K");
        System.out.println("heap memory usage: " + memoryMXBean.getHeapMemoryUsage());
        System.out.println("non-heap memory usage: " + memoryMXBean.getNonHeapMemoryUsage());
        System.out.println("total memory: " + Runtime.getRuntime().totalMemory() / 1024);
        System.out.println("free memory: " + Runtime.getRuntime().freeMemory() / 1024);
        System.out.println("max memory: " + Runtime.getRuntime().maxMemory() / 1024);
    }
}
