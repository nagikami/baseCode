package nagi.exercise.jianzhi;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 逆序输出链表
 */
public class JZ6 {
    /**
     * 使用栈
     * @param listNode
     * @return
     */
    public ArrayList<Integer> PrintListFromTailToHead(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        ArrayList<Integer> integers = new ArrayList<>();
        while (!stack.isEmpty()) {
            integers.add(stack.pop());
        }
        return integers;
    }

    /**
     * 使用列表按索引插入，类似头插法
     * @param listNode
     * @return
     */
    public ArrayList<Integer> PrintListFromTailToHeadWithIndex(ListNode listNode) {
        ArrayList<Integer> integers = new ArrayList<>();
        while (listNode != null) {
            integers.add(0,listNode.val);
            listNode = listNode.next;
        }
        return integers;
    }
}
