package nagi.java.csapp;

import sun.misc.Unsafe;

import static nagi.java.unsafe.UnsafeTest.getUnsafe;

public class BitOperation {
    public static void main(String[] args) {
        // 算数右移，符号扩展，使用最高位填充
        System.out.println(-2 >> 1);
        // 逻辑右移，使用0填充
        System.out.println(-2 >>> 1);
        Unsafe unsafe = getUnsafe();
        long allocateMemory = unsafe.allocateMemory(4);
        unsafe.putAddress(allocateMemory, 300);
        System.out.println(unsafe.getByte(allocateMemory));
        System.out.println(unsafe.getByte(allocateMemory + 1));
        unsafe.freeMemory(allocateMemory);
        // 补码存储时-x = ~x + 1
        System.out.println(~10 + 1);
        System.out.println(~-10 + 1);
        multiByBit();
        delByBit();
    }

    private static void multiByBit() {
        // 3 * 6，编译器通过将6拆解为2 ^ 2 + 2将乘法转化为移位运算和加法，从而降低乘法的运算开销
        System.out.println((3 << 2) + (3 << 1));
    }

    private static void delByBit() {
        // 向下舍入
        System.out.println(7 >> 2);
        // x / 2 ^ k向上舍入，通过加偏置量(1 << k) - 1即2 ^ k - 1实现向上舍入
        System.out.println((7 + (1 << 2) - 1) >> 2);
    }
}
