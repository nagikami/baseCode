package nagi.exercise;

import java.util.HashMap;
import java.util.Map;

/**
 * 1
 */
public class TwoNumAdd {
    public static void main(String[] args) {
        int[] ints = twoNumAdd(new int[]{1, 2, 3, 5, 7}, 10);
    }

    static int[] twoNumAdd(int[] arrays, int target) {
        Map<Integer, Integer> storeMap = new HashMap<>();
        int[] result = new int[2];
        for (int i = 0; i < arrays.length; i++) {
            int another = target - arrays[i];
            if (storeMap.containsKey(another)) {
                result[1] = i;
                result[0] = storeMap.get(another);
                break;
            } else {
                storeMap.put(arrays[i], i);
            }
        }
        return result;
    }
}
