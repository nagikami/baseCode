package nagi.exercise.jianzhi;

import java.util.*;

/**
 * 之字形遍历二叉树
 */
public class JZ27 {
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        Queue<Node> nodes = new LinkedList<>();
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (pRoot == null) {
            return lists;
        }
        nodes.offer(new Node(1, pRoot));
        HashMap<Integer, ArrayList<Integer>> integerListHashMap = new HashMap<>();
        while (!nodes.isEmpty()) {
            Node node = nodes.poll();
            int depth = node.depth;
            TreeNode treeNode = node.treeNode;
            if (treeNode.left != null) {
                nodes.add(new Node(depth + 1, treeNode.left));
            }
            if (treeNode.right != null) {
                nodes.add(new Node(depth + 1,treeNode.right));
            }
            ArrayList<Integer> integers = integerListHashMap.computeIfAbsent(depth, k -> new ArrayList<>());
            integers.add(node.treeNode.val);
        }
        for (Integer integer : integerListHashMap.keySet()) {
            ArrayList<Integer> integers = integerListHashMap.get(integer);
            if (integer % 2 == 0) {
                Collections.reverse(integers);
            }
            lists.add(integers);
        }
        return lists;
    }

    class Node {
        int depth;
        TreeNode treeNode;

        public Node(int depth, TreeNode treeNode) {
            this.depth = depth;
            this.treeNode = treeNode;
        }
    }

    /**
     * 按层处理
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> PrintByFloor(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
        Queue<TreeNode> treeNodes = new LinkedList<>();
        if (Objects.isNull(pRoot)) {
            return arrayLists;
        }
        treeNodes.offer(pRoot);
        boolean flag = false;
        while (!treeNodes.isEmpty()) {
            // 当前层节点数
            int size = treeNodes.size();
            ArrayList<Integer> integers = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = treeNodes.poll();
                if (Objects.nonNull(node.left)) {
                    treeNodes.offer(node.left);
                }
                if (Objects.nonNull(node.right)) {
                    treeNodes.offer(node.right);
                }
                integers.add(node.val);
            }
            if (flag) {
                Collections.reverse(integers);
            }
            flag = !flag;
            arrayLists.add(integers);
        }
        return arrayLists;
    }

    /**
     * 双栈法
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> PrintByTwoStacks(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        if (Objects.isNull(pRoot)) {
            return lists;
        }
        // 奇数层栈
        Stack<TreeNode> oddStack = new Stack<>();
        // 偶数层栈
        Stack<TreeNode> evenStack = new Stack<>();
        oddStack.push(pRoot);
        while (!oddStack.isEmpty() || !evenStack.isEmpty()) {
            ArrayList<Integer> oddIntegers = new ArrayList<>();
            while (!oddStack.isEmpty()) {
                TreeNode node = oddStack.pop();
                // 顺序入偶数栈
                if (Objects.nonNull(node.left)) {
                    evenStack.push(node.left);
                }
                if (Objects.nonNull(node.right)) {
                    evenStack.push(node.right);
                }
                oddIntegers.add(node.val);
            }
            if (!oddIntegers.isEmpty()) {
                lists.add(oddIntegers);
            }
            ArrayList<Integer> evenIntegers = new ArrayList<>();
            while (!evenStack.isEmpty()) {
                TreeNode node = evenStack.pop();
                // 逆序入奇数栈
                if (Objects.nonNull(node.right)) {
                    oddStack.push(node.right);
                }
                if (Objects.nonNull(node.left)) {
                    oddStack.push(node.left);
                }
                evenIntegers.add(node.val);
            }
            if (!evenIntegers.isEmpty()) {
                lists.add(evenIntegers);
            }
        }
        return lists;
    }
}
