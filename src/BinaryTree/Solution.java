package BinaryTree;


import java.util.ArrayList;
import java.util.List;

class TreeNode {
    TreeNode left;
    TreeNode right;
    int data;
    public TreeNode() {
    }
    public TreeNode(int data) {
        this.data = data;
    }
    public TreeNode(TreeNode left, TreeNode right, int data) {
        this.left = left;
        this.right = right;
        this.data = data;
    }
}

class Solution {

    public static void main(String[] args) {

        TreeNode left1 = new TreeNode(null, null, 12);
        TreeNode left = new TreeNode(left1, null, 11);
        TreeNode right = new TreeNode(null, null, 13);

        TreeNode treeNode = new TreeNode(left, right, 10 );

        Solution solution = new Solution();
        System.out.println(solution.isBalanced(treeNode));

    }

    public boolean isBalanced(TreeNode root) {

        return false;
        //return helpMethod(root, 0);
    }

    /*private boolean helpMethod(TreeNode root, int height) {

        if(heightDiff > 1) {
            return false;
        }

        if(root == null) {
            height = height + 1;
        }

    }*/
}