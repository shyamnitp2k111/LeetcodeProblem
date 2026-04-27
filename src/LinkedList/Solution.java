package LinkedList;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);


        System.out.println("----------- Before --------------");
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }

        head  = solution.deleteDuplicates(head);

        System.out.println("----------- After --------------");
       // System.out.println("answer is "+ value);
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }
    }

    public ListNode deleteDuplicates(ListNode head) {

        ListNode prev = null;
        ListNode current = head;

        while (current != null) {
            while (current.next != null && current.val == current.next.val) {
                current = current.next;
            }

            if(prev == null) {
                ListNode temp = current;
                current = current.next;
                head = current;
                temp.next = null;
            } else {
                prev.next = current.next;
                current.next = null;
                current = prev.next;
            }

            prev = current;
        }

        return head;
    }
}