package LinkedList;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

class Solution3 {

    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);


        System.out.println("----------- Before --------------");
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }

         head  = solution.reverseBetween(head, 2,4);

        System.out.println("----------- After --------------");
        // System.out.println("answer is "+ value);
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }
    }


    public ListNode reverseBetween(ListNode head, int left, int right) {

        if(head == null || left == right) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode prev = dummy;

        for(int i = 1; i < left; i++){
            prev = prev.next;
        }

        ListNode curr = prev.next;

        for(int i = 0; i < right - left; i++){
            ListNode next = curr.next;
            curr.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }

        return dummy.next;
    }
}