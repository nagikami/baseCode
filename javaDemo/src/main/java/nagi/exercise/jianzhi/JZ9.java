package nagi.exercise.jianzhi;

import java.util.Stack;

/**
 * 用两个栈实现队列
 */
public class JZ9 {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        stack1.push(node);
    }

    public int pop() {
        // 栈2为空则将栈1入栈2，否则直接返回栈顶
        if (stack2.isEmpty()) {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
        }
        return stack2.pop();
    }
}
