package nagi.exercise.jianzhi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 二插搜索树的第k个节点
 */
public class JZ54 {
    public int KthNode(TreeNode proot, int k) {
        List<Integer> nodes = new ArrayList<>();
        if (Objects.isNull(proot) || k == 0) {
            return -1;
        }
        dfs(proot, nodes);
        Collections.sort(nodes);
        if (nodes.size() < k) {
            return -1;
        }
        return nodes.get(k - 1);
    }

    public void dfs(TreeNode node, List<Integer> nodes) {
        if (Objects.isNull(node)) {
            return;
        }
        nodes.add(node.val);
        dfs(node.left, nodes);
        dfs(node.right, nodes);
    }

    /**
     * 二叉搜索树中序遍历为递增序
     * @param proot
     * @param k
     * @return
     */
    private TreeNode res = null;
    private int count = 0;
    public int KthNodeMiddle(TreeNode proot, int k) {
        midOrder(proot, k);
        if (Objects.nonNull(res)) {
            return res.val;
        }
        return -1;
    }

    public void midOrder(TreeNode node, int k) {
        // 节点为空或者节点数超过k时返回
        if (Objects.isNull(node) || count > k) {
            return;
        }
        // 遍历左子树
        midOrder(node.left, k);
        // 计数
        count++;
        if (count == k) {
            res = node;
        }
        midOrder(node.right, k);
    }
}
