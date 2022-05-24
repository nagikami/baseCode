package nagi.exercise.jianzhi;

/**
 * 二维数组中的查找
 */
public class JZ4 {
    /**
     * 二分法，左下大于上小于右
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int [][] array) {
        int m = array.length;
        if (m == 0) {
            return false;
        }
        int n = array[0].length;
        if (n == 0) {
            return false;
        }
        for (int i = m -1, j = 0; i >= 0 && j < n;) {
            int val = array[i][j];
            if (val > target) {
                // 比目标大，向上移动
                i--;
            } else if (val < target) {
                // 比目标小，向右移动
                j++;
            } else {
                return true;
            }
        }
        return false;
    }
}
