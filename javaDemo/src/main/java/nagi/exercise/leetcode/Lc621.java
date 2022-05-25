package nagi.exercise.leetcode;

/**
 * CPU调度
 */
public class Lc621 {
    public int leastInterval(char[] tasks, int n) {
        int[] types = new int[26];
        // 最大任务数
        int maxCount = 0;
        // 最大任务数任务类型数
        int count = 0;
        for (char task : tasks) {
            int index = task - 'A';
            types[index]++;
            int val = types[index];
            // 等于当前最大任务数，则类型数自增
            if (val == maxCount) {
                count++;
            } else if(val > maxCount) {
                // 重置最大任务数和类型数
                maxCount = val;
                count = 1;
            }
        }
        // 结果为任务数量和最大任务数执行完且可能有空缺的最大值
        return Math.max(tasks.length, (maxCount - 1) * (n + 1) + count);
    }
}
