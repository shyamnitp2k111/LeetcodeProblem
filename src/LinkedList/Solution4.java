package LinkedList;

import java.util.*;

class Solution4 {

    public static void main(String[] args) {
        Solution4 solution4 = new Solution4();

        ListNode head = new ListNode(2);
        head.next = new ListNode(1);
        head.next.next = new ListNode(5);
        //head.next.next.next = new ListNode(4);
        //head.next.next.next.next = new ListNode(5);


        System.out.println("----------- Before --------------");
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }


        System.out.println("----------- After --------------");

        Arrays.stream(solution4.nextLargerNodes(head)).forEach(System.out::println);

        /*System.out.println("----------- After --------------");
        // System.out.println("answer is "+ value);
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }*/
    }


    public int[] nextLargerNodes(ListNode head) {

        Stack<ListNode> stack = new Stack<>();

        int length = 0;

        for(ListNode index = head ; index != null ; index = index.next) {

            while (!stack.isEmpty() && index.val > stack.peek().val) {
                ListNode node = stack.pop();
                node.val = index.val;
            }
            stack.push(index);
            length++;
        }

        while (!stack.isEmpty()) {
            stack.pop().val = 0;
        }

        int[] results = new int[length];
        int i = 0;
        for(ListNode index = head ; index != null ; index =index.next) {
            results[i++] = index.val;
        }

        return results;
    }
}