package nagi.exercise.leetcode;

import java.util.*;

/**
 * 拼接最大数
 */
public class Lc179 {
    public String largestNumber(int[] nums) {
        List<String> results = new ArrayList<>();
        for (int num : nums) {
            results.add(String.valueOf(num));
        }
        Collections.sort(results, (o1, o2) -> {
            // 长度相同，大数在前
            if (o1.length() == o2.length()) {
                return o2.compareTo(o1);
            } else {
                // 长度不同，拼接后大的在前
                return (o2 + o1).compareTo(o1 + o2);
            }
        });
        StringBuilder stringBuilder = new StringBuilder();
        for (String result : results) {
            stringBuilder.append(result);
        }
        String result = stringBuilder.toString();
        return result.startsWith("0") ? "0" : result;
    }
}
