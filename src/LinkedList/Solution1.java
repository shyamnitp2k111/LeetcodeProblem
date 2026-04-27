package LinkedList;

class Solution1 {

    public static void main(String[] args) {
        Solution1 solution1 = new Solution1();
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);


        System.out.println("----------- Before --------------");
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }

        head  = solution1.rotateRight(head, 1);

        System.out.println("----------- After --------------");
        // System.out.println("answer is "+ value);
        for(ListNode iterator = head; iterator !=null ; iterator = iterator.next ) {
            System.out.println(iterator.val);
        }
    }


    public ListNode rotateRight(ListNode head, int k) {

        if (k == 0) {
            return head;
        }
        if (head == null) {
            return null;
        }

        if (head.next == null) {
            return head;
        }

        int length = 0;
        ListNode lastNode =null;
        for (ListNode iterator = head; iterator != null; iterator = iterator.next) {
            length++;
            lastNode = iterator;
        }

        lastNode.next = head;
        System.out.println(length);


        k = k % length;
        for(int index = 0 ; index < length - k ; index++) {
            head = head.next;
            lastNode = lastNode.next;
        }

        lastNode.next = null;

       return head;
    }
}