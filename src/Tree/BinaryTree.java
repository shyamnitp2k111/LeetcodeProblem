package Tree;


import java.util.*;

class TreeNode {
    TreeNode left;
    TreeNode right;
    int val;
    public TreeNode() {
    }
    public TreeNode(int data) {
        this.val = data;
    }
    public TreeNode(TreeNode left, TreeNode right, int data) {
        this.left = left;
        this.right = right;
        this.val = data;
    }
}

class Solution {

    public static void main(String[] args) {

        TreeNode left1 = new TreeNode(null, null, 12);
        TreeNode left = new TreeNode(left1, null, 11);
        TreeNode right = new TreeNode(null, null, 13);

        TreeNode treeNode = new TreeNode(left, right, 10 );

        Solution solution = new Solution();
        solution.levelOrderTraversal(treeNode);
        System.out.println(solution.sumOfLeftLeaves(treeNode));


        System.out.println("------------ find mode ------------");
        int[] val = solution.findMode(treeNode);
        Arrays.stream(val).forEach(System.out::println);


        System.out.println("------------ get Minimum Difference ------------");
        System.out.println(solution.getMinimumDifference(treeNode));
    }

    public int getMinimumDifference(TreeNode root) {

        if(root == null) {
            return 0;
        }

        int prev = getMinimumDifference(root.left);

        int min = Math.min(prev, root.val);

        getMinimumDifference(root.right);
        return min;
    }

    public int[] findMode(TreeNode root) {
        Map<Integer, Integer> map = new HashMap<>();

        helperFindModes(root, map);

        int max = map.values().stream().mapToInt(i->i).max().getAsInt();

        List<Integer> list = map.entrySet().stream().
                filter(i -> i.getValue() == max)
                .map(Map.Entry::getKey)
                .toList();

        return list.stream().mapToInt(i -> i).toArray();
    }

    private void helperFindModes(TreeNode root, Map<Integer, Integer> map) {

        if(root == null) {
            return;
        }

        map.put(root.val, map.getOrDefault(root.val, 0) + 1);
        helperFindModes(root.left, map);
        helperFindModes(root.right, map);
    }

    public void levelOrderTraversal(TreeNode root) {

        System.out.println("------ level order traversal -------");
        if(root == null) {
            return;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();

        queue.offer(root);


        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size > 0) {
                TreeNode treeNode = queue.poll();
                System.out.print("   "+treeNode.val);
                size--;
                if(treeNode.left != null) {
                    queue.offer(treeNode.left);
                }

                if(treeNode.right != null) {
                    queue.offer(treeNode.right);
                }
            }
            System.out.println("");
        }

        System.out.println(" ---------------------------- ");
    }


    public int sumOfLeftLeaves(TreeNode root) {
        int sum =0;
        sum = helperMethods(root, sum,false);
        return sum;
    }

    private int helperMethods(TreeNode root, int sum, boolean isLeft) {

        if(root == null) {
            return sum;
        }

        if(root.left == null && root.right == null) {

            if(isLeft) {
                sum += root.val;
                System.out.println("sum is " + sum);
                return sum;
            }
        }

        sum = helperMethods(root.left, sum, true);
        sum = helperMethods(root.right, sum, false);
        return sum;
    }

}