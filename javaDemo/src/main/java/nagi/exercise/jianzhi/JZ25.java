package nagi.exercise.jianzhi;

/**
 * 二叉树深度
 */
public class JZ25 {
    public int TreeDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return Math.max(TreeDepth(root.left), TreeDepth(root.right)) + 1;
    }
}
