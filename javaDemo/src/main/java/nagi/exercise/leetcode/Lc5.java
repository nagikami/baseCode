package nagi.exercise.leetcode;

/**
 * 最长回文子串
 */
public class Lc5 {
    public String longestPalindrome(String s) {
        int length = s.length();
        if (length < 2) {
            return s;
        }
        // 状态矩阵保存ij是否为回文
        boolean[][] dp = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }
        int start = 0;
        int maxLength = 1;

        // 区间由短到长
        for (int L = 2; L <= length; L++) {
            for (int i = 0; i < length; i++) {
                int j = i + L - 1;
                if (j >= length) {
                    break;
                }
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    // 相邻且相同为true
                    if (j - i < 2) {
                        dp[i][j] = true;
                    } else {
                        // 端点相同为两端点之间区间结果
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j] && j - i + 1 > maxLength) {
                    // 记录最大值
                    maxLength = j - i + 1;
                    // 记录起始位置
                    start = i;
                }
            }
        }
        return s.substring(start, start + maxLength);
    }
}
