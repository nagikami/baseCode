package nagi.exercise.jianzhi;

/**
 * 连续子数组的最大和
 */
public class JZ42 {
    public int FindGreatestSumOfSubArray(int[] array) {
        int length = array.length;
        // 以i结尾的最大子数组和
        int[] dp = new int[length];
        int max = array[0];
        dp[0] = array[0];
        for (int i = 1; i < length;i++) {
            // 状态转移，确定dp[i]
            dp[i] = Math.max(dp[i - 1] + array[i], array[i]);
            // 保存最大值
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public int FindGreatestSumOfSubArrayImprove(int[] array) {
        int length = array.length;
        // 保存当前连续和最大值
        int sum = array[0];
        int max = array[0];
        for (int i = 1; i < length;i++) {
            // 状态转移，保存当前最大和
            sum = Math.max(sum + array[i], array[i]);
            // 保存最大值
            max = Math.max(max, sum);
        }
        return max;
    }
}
