package nagi.java.csapp;

public class Condition {
    public static void main(String[] args) {
        System.out.println(source(0));
        System.out.println(controller(0));
        System.out.println(data(0));
    }

    /**
     * 源代码
     * @return
     */
    private static int source(int a) {
        int x = 10;
        int y = 20;
        if (a >= 0) {
            return x + y;
        } else {
            return x - y;
        }
    }

    /**
     * 机器码逻辑模拟
     * 使用控制流处理分支
     * 处理器会进行分支预测（成功率不确定），判断是否会进行跳转，从而在pipeline预处理指令，分支预测失败的代价较高
     * @return
     */
    private static int controller(int a) {
        int x = 10;
        int y = 20;
        // 使用比较指令（cmp、test，只修改条件码不修改寄存器）设置条件码
        if (a < 0) {
            // 根据条件码进行条件跳转（jl），下面的代码在机器码时会在最后，Java没有goto，故放在此处模拟
            return x - y;
        }
        return x + y;
    }

    /**
     * 机器码逻辑模拟
     * 使用数据流处理分支
     * pipeline同时处理两个分支的指令（保证指令运行满载），使用条件传送指令保存结果
     * 需在特定条件下才可提高执行效率（例如两个分支的运算都很简单且执行两个分支不会发生报错），因此处理器很少进行此优化
     * @return
     */
    private static int data(int a) {
        int x = 10;
        int y = 20;
        // 同时执行两个分支
        int re = x - y;
        int vre = x + y;
        // 使用条件传送指令保存结果（cmovge）
        if (a >= 0) {
            re = vre;
        }
        return re;
    }
}
