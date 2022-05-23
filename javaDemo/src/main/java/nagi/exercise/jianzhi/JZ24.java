package nagi.exercise.jianzhi;

import java.util.Stack;

/**
 * 链表逆转
 */
public class JZ24 {

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode listNode = new ListNode(2);
        ListNode listNode1 = new ListNode(3);
        head.next = listNode;
        listNode.next = listNode1;
        JZ24 jz24 = new JZ24();
        jz24.reverseListWithThreePointer(head);
    }

    public ListNode reverseListWithThreePointer(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode before = head;
        ListNode current = before.next;
        ListNode after = current.next;
        while (after != null) {
            current.next = before;
            before = current;
            current = after;
            after = after.next;
        }
        current.next = before;
        head.next = null;
        return current;
    }

    /**
     * 使用栈的尾插法
     * @param head
     * @return
     */
    public ListNode reverseListWithStack(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        if (head == null) {
            return head;
        }
        while (head != null) {
            stack.push(head);
            head = head.next;
        }
        ListNode tail = stack.pop();
        // 记录新链表头
        ListNode dummy = tail;
        while (!stack.isEmpty()) {
            tail.next = stack.pop();
            tail = tail.next;
        }
        tail.next = null;
        return dummy;
    }

    /**
     * 头插法
     * @param head
     * @return
     */
    public ListNode reverseListWithHeadInsert(ListNode head) {
        ListNode newHead = null;
        while (head != null) {
            // 记住下一个
            ListNode after = head.next;
            // 指向新节点
            head.next = newHead;
            // 记住当前头节点
            newHead = head;
            // 移动到下一个
            head = after;
        }
        return newHead;
    }


    /**
     * 递归调用
     * @param head
     * @return
     */
    public ListNode reverseListWithRecursive(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        ListNode listNode = reverseListWithRecursive(next);
        next.next = head;
        head.next = null;
        return listNode;
    }
}
