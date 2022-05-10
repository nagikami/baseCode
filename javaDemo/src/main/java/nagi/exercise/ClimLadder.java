package nagi.exercise;

import java.util.HashMap;
import java.util.Map;

/**
 * 70 (爬楼梯，斐波那契数列)
 */
public class ClimLadder {

    private static Map<Integer, Integer> storeMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println(climLadder(3));
        System.out.println(climLadderWithStore(10));
        System.out.println(climLadderAccumulator(10));
    }

    //直接递归 O(n^2)
    static int climLadder(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return climLadder( n - 1) + climLadder(n - 2);
    }

    //使用map保存结果 O(n)
    static int climLadderWithStore(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (storeMap.get(n) != null) {
            return storeMap.get(n);
        } else {
            int result = climLadder( n - 1) + climLadder(n - 2);
            storeMap.put(n, result);
            return result;
        }
    }

    //累加计算 O(n)
    static int climLadderAccumulator(int n) {
        int pre = 2;
        int prePre = 1;
        int result = 0;
        for (int i = 3; i <= n; i++) {
            result = pre + prePre;
            prePre = pre;
            pre = result;
        }
        return result;
    }
}
