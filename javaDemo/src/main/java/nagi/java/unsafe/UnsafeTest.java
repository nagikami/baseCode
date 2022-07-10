package nagi.java.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeTest {
    public static void main(String[] args) {
        try {
            Unsafe unsafe = getUnsafe();
            long allocateMemory = unsafe.allocateMemory(4);
            unsafe.putAddress(allocateMemory, 1);
            int a = unsafe.getInt(allocateMemory);
            System.out.println(a);
            unsafe.freeMemory(allocateMemory);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe)theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
