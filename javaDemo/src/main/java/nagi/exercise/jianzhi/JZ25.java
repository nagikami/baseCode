package nagi.exercise.jianzhi;

import java.util.Objects;

/**
 * 合并两个有序链表
 */
public class JZ25 {
    /**
     * 递归
     * @param list1
     * @param list2
     * @return
     */
    public ListNode Merge(ListNode list1,ListNode list2) {
        if (Objects.isNull(list1)) {
            return list2;
        }
        if (Objects.isNull(list2)) {
            return list1;
        }
        if (list1.val > list2.val) {
            list2.next = Merge(list1, list2.next);
            return list2;
        } else {
            list1.next = Merge(list1.next, list2);
            return list1;
        }
    }

    /**
     * 非递归
     * @param list1
     * @param list2
     * @return
     */
    public ListNode MergeList(ListNode list1,ListNode list2) {
        // 哨兵节点
        ListNode dummy = new ListNode(-1);
        // 虚拟头结点
        ListNode result = dummy;
        while (Objects.nonNull(list1) && Objects.nonNull(list2)) {
            if (list1.val > list2.val) {
                dummy.next = list2;
                list2 = list2.next;
                dummy = dummy.next;
            } else {
                dummy.next = list1;
                list1 = list1.next;
                dummy = dummy.next;
            }
        }
        if (Objects.nonNull(list1)) {
            dummy.next = list1;
        }
        if (Objects.nonNull(list2)) {
            dummy.next = list2;
        }
        return result.next;
    }
}
