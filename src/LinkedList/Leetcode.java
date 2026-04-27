package LinkedList;

import java.security.spec.RSAOtherPrimeInfo;

public class Leetcode {

    public static void main(String[] args) {
        Leetcode solution = new Leetcode();
        ListNode head = new ListNode(0);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(0);
        head.next.next.next.next = new ListNode(5);
        head.next.next.next.next.next = new ListNode(0);


        System.out.println("----------- Before --------------");
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }

        head = solution.mergeNodes(head);

        System.out.println("----------- After --------------");
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }

    }

    public ListNode mergeNodes(ListNode head) {

        ListNode headResult = new ListNode(-1);
        ListNode newHead = headResult;
        int sum = 0;
        for(ListNode current = head.next; current != null; current = current.next) {
            if(current.val == 0 ) {
                ListNode newNode = new ListNode();
                newNode.val = sum;
                headResult.next = newNode;
                headResult = newNode;
                sum = 0;
            }
            sum += current.val;
        }

        return newHead.next;
    }
}
