package nagi.exercise.jianzhi;

/**
 * 不用加减乘除做加法
 */
public class JZ65 {
    public int Add(int num1,int num2) {
        // 非进位和值
        int sum = num1;
        // 进位值
        int add = num2;
        while (add != 0) {
            // 保存非进位和值
            int temp = sum ^ add;
            // 获取进位值
            add = (sum & add) << 1;
            // 更新非进位和值
            sum = temp;
        }
        return sum;
    }
}
