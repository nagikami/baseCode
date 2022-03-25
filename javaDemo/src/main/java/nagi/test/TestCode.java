package nagi.test;

import org.junit.Test;

public class TestCode {
    public static void main(String[] args) {
        //单调时钟统计持续时间
        long startTime = System.nanoTime();
        int count = 0;
        for (int i = 0; i < 1000; i++) {
            count++;
        }
        long endTime = System.nanoTime();
        System.out.println(count + ": " + (endTime - startTime));
    }

    @Test
    public void testM() {
        System.out.println(frog(3));
    }

    public int frog(int n) {
        if (n == 1) {
            return 1;
        } else if(n == 2) {
            return 2;
        }

        return frog(n - 1) + frog(n - 2);
    }

    /**
     * get maximum
     * @param a
     * @param b
     * @return
     */
    public int max(int a, int b) {
        int x = (int)(((long)a - (long)b) >> 63 & 1);
        int[] ints = {a, b};
        return ints[x];
    }

    /**
     * calculate fib
     * @param n
     * @return
     */
    public int fib(int n) {
        int res = 0;
        int before = 1;
        int current = 1;
        if (n < 3) {
            res = 1;
        }
        for (int i = 2; i < n; i++) {
            res = before + current;
            before = current;
            current = res;
        }
        return res;
    }

    /**
     * 图片平滑器
     * @param img
     * @return
     */
    public int[][] imageSmoother(int[][] img) {
        int m = img.length;
        int n  = img[0].length;
        int[][] res = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int num = 0;
                int sum = 0;
                for (int k = i - 1; k < i + 2; k++) {
                    for (int l = j - 1; l < j + 2; l++) {
                        if (k >= 0 && k < m && l >= 0 && l < n) {
                            sum += img[k][l];
                            num++;
                        }
                    }
                }
                res[i][j] = sum / num;
            }
        }
        return res;
    }

    /**
     * 最长回文子串
     */
    public String longestPalindrome(String s) {
        int length = s.length();
        if (s.length() < 2) {
            return s;
        }
        boolean[][] dp = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }
        int start = 0;
        int maxLength = 1;

        for (int L = 2; L <= length; L++) {
            for (int i = 0; i < length; i++) {
                int j = i + L - 1;
                if (j >= length) {
                    break;
                }
                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 2) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                if (dp[i][j] && j - i + 1 > maxLength) {
                    maxLength = j - i + 1;
                    start = i;
                }
            }
        }
        return s.substring(start, start + maxLength);
    }
}
