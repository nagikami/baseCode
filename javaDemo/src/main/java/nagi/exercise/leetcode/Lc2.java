package nagi.exercise.leetcode;

/**
 * 两数相加
 */
public class Lc2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null) {
            // 获取当前位的值，没有默认赋0
            int n1 = l1 != null ? l1.val : 0;
            int n2 = l2 != null ? l2.val : 0;
            // 两数之和加进位
            int sum = n1 + n2 + carry;
            if (head == null) {
                head = tail = new ListNode(sum % 10);
            } else {
                // 当前位值为两数之和加进位mod10
                tail.next = new ListNode(sum % 10);
                tail = tail.next;
            }
            // 进位
            carry = sum / 10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        // 最大位仍有进位，新增一位值为进位
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }
        return head;
    }
}
