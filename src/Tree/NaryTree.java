package Tree;

import java.util.List;

class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, List<Node> _children) {
        val = _val;
        children = _children;
    }
};


class Solution1 {

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        solution1.helpMethod(null, 0);
    }
    public int maxDepth(Node root) {
        return helpMethod(root, 0);
    }

    private int helpMethod(Node root, int count) {

        if(root == null) {
            return 0;
        }

        if(root.children.isEmpty()) {
            return 1;
        }

        for(int index = 0 ; index < root.children.size(); index++) {
            return Math.max(helpMethod(root.children.get(index), count), helpMethod(root.children.get(index), count));
        }
        return 0;
    }
}