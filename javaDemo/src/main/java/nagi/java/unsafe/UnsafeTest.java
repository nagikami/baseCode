package nagi.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {
    public static void main(String[] args) {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            Unsafe unsafe = (Unsafe)theUnsafe.get(null);
            long allocateMemory = unsafe.allocateMemory(4);
            unsafe.putAddress(allocateMemory, 1);
            int a = unsafe.getInt(allocateMemory);
            System.out.println(a);
            unsafe.freeMemory(allocateMemory);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
