package nagi.java.csapp;

public class Overflow {
    public static void main(String[] args) {
        // x + y - 2 ^ 32
        System.out.printf("positive overflow: %d, isAddOk: %b%n", Integer.MAX_VALUE + 1, isAddOk(Integer.MAX_VALUE, 1));
        // x + y + 2 ^ 32
        System.out.printf("negative overflow: %d, isSubOk: %b%n", Integer.MIN_VALUE - 1, isSubOk(Integer.MIN_VALUE, 1));
    }

    /**
     * 判断加法是否会发生溢出
     * @param x
     * @param y
     * @return true：不会发生溢出， false：会发生溢出
     */
    private static boolean isAddOk(int x, int y) {
        int z = x + y;
        // 发生正溢出
        if (x > 0 && y > 0 && z < 0) {
            return false;
        }
        // 发生负溢出
        if (x < 0 && y < 0 && z >= 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断减法是否会发生溢出
     * @param x
     * @param y
     * @return true：不会发生溢出， false：会发生溢出
     */
    private static boolean isSubOk(int x, int y) {
        // 由于有符号数正负不对称，-Integer.MIN_VALUE == Integer.MIN_VALUE会导致isAddOk()判断错误
        if (Integer.MIN_VALUE == y) {
            return x < 0;
        }
        return isAddOk(x, -y);
    }

    /**
     * 判断乘法是否会溢出，因为在x * y > 2 ^ 31 - 1时(x * y) % 2 ^ 32 / x != (x * y) / x
     * 而(x + y) % 2 ^ 32 - x == y，所以同样的方法不可用于判断加法溢出
     * @param x
     * @param y
     * @return
     */
    private static boolean isMultiOk(int x, int y) {
        int z = x * y;
        return x == 0 || z / x == y;
    }
}
