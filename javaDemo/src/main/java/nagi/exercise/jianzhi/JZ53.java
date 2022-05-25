package nagi.exercise.jianzhi;

/**
 * 数字在升序数组中出现的次数
 */
public class JZ53 {

    public static void main(String[] args) {
        JZ53 jz53 = new JZ53();
        jz53.GetNumberOfK(new int[] {1,2,3,3,3,3,4,5}, 3);
    }

    public int GetNumberOfK(int [] array , int k) {
        int index = find(array, k, 0, array.length);
        if (index == -1) {
            return 0;
        } else {
            int count = 1;
            int left = index - 1;
            while (left >= 0 && array[left] == k) {
                if (array[left] == k) {
                    count++;
                    left--;
                }
            }
            int right = index + 1;
            while (right < array.length && array[right] == k) {
                if (array[right] == k) {
                    count++;
                    right++;
                }
            }
            return count;
        }
    }

    public int find(int[] array, int k, int left, int right) {
        if (left >= right) {
            return -1;
        }
        int result;
        int mid = (left + right) / 2;
        if (array[mid] == k) {
            return mid;
        } else if (array[mid] < k) {
            result = find(array, k, mid + 1, right);
        } else {
            result = find(array, k, left, mid);
        }
        return result;
    }
}
