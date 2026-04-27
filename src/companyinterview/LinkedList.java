package companyinterview;

public class LinkedList {

    static class LinkedLists{
        private int data;
        private LinkedLists next;

        public LinkedLists(int data, LinkedLists next) {
            this.data = data;
            this.next = next;
        }

        public LinkedLists() {
        }

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }

        public LinkedLists getNext() {
            return next;
        }

        public void setNext(LinkedLists next) {
            this.next = next;
        }
    }
    public static void main(String[] args) {


        LinkedLists headDummy = new LinkedLists(-1, null) ;
        for(int index = 0 ; index < 100; index ++) {
                LinkedLists linkedLists = new LinkedLists(100 - index, null);
                linkedLists.next = headDummy.next;
                headDummy.next = linkedLists;
        }

        for(LinkedLists start = headDummy.next ; start != null ; start = start.next) {
            System.out.print(start.getData() +" ");
        }


        //change value from 25 to 30
        headDummy = changeValueForLinkedList(headDummy);

        System.out.println("After changes");

        for(LinkedLists start = headDummy.next ; start != null ; start = start.next) {
            System.out.println(start.getData());
        }


        //delete 5 to 10
        System.out.println("After deletion operation  changes");

        //deleteElementFromNode(headDummy);

        for(LinkedLists start = headDummy.next ; start != null ; start = start.next) {
            System.out.println(start.getData());
        }



        headDummy = reverseList(headDummy.next);
        System.out.println("After reverse operation  changes --------------------------------------");
        for(LinkedLists start = headDummy.next ; start != null ; start = start.next) {
            System.out.println(start.getData());
        }

    }

    /*private static void deleteElementFromNode(LinkedLists headDummy) {

        LinkedLists start = headDummy;
        while(start != null) {
            start.next;
        }


    }*/

    public static LinkedLists reverseList(LinkedLists head) {

        LinkedLists prevNode = null;
        LinkedLists current = head;

        while(current != null) {
            LinkedLists temp = current.next;
            current.next = prevNode;
            prevNode = current;
            current =temp;
        }

        return prevNode;
    }

    private static LinkedLists changeValueForLinkedList(LinkedLists headDummy) {

        boolean flag = false;
        for(LinkedLists start = headDummy.next ; start != null  && !flag; start = start.next) {

            int changeNodeValue = 5;
            int i = 1;

            while(start != null && changeNodeValue > 0 && start.data > 25) {

                start.data = 100 * i;
                start = start.next;
                changeNodeValue--;
                i++;
                flag = true;
            }
        }
        return headDummy;
    }


}
