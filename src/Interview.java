import java.util.HashMap;
import java.util.Map;

public class Interview {

    // class -> LRU
    // get
    // put - insert and update
    // fixed size -> 3
    //
    // o(1) and o(1)
    //
    // Map
    // doubly linked list

    /*

       1, 2 ,3 , put(4) , get(2)


       2 -> NodeAddress
       3 -> NodeAddress
       4 -> NodeAddress

       start           end
          2  4  3

     */

    static void main() {

    }
}

class LRUCache {
    int MAX_SIZE = 3;
    int count = 0;

    Map<Integer, DoublyLinkedList> map = new HashMap<>();
    DoublyLinkedList start = null;
    DoublyLinkedList end = null;

    public LRUCache(int size) {
        this.MAX_SIZE = size;
    }

     //3 , 1, 2
    public boolean putCache(int key, String value) {
        if(count < MAX_SIZE) {
            if(!map.containsKey(key)) {

                DoublyLinkedList doublyLinkedList = insertData(key, value);
                map.put(key,doublyLinkedList );
                count++;

            } else {
                //


                DoublyLinkedList doublyLinkedList = map.get(key);
                map.remove(key);
                removeNode(key, doublyLinkedList);
                DoublyLinkedList doublyLinkedList1 = insertData(key, value);
                map.put(key, doublyLinkedList1);


            }
        } else {


            if(!map.containsKey(key)) {
                int removeKey = removeNodeFromEnd();
                map.remove(removeKey);
                DoublyLinkedList startNode = insertData(key, value);
                map.put(key, startNode);
                count++;


            } else {
                removeNode(key , map.get(key));

                DoublyLinkedList doublyLinkedList = insertData(key, value);
                map.put(key, doublyLinkedList);
            }
        }

        return true;
    }

    //1, 2, 3
    private int removeNodeFromEnd() {
        DoublyLinkedList endNew = end;
        end = end.previous;
        end.next = null;
        return endNew.key;
    }

    private void removeNode(int key, DoublyLinkedList doublyLinkedList) {
    }

    private DoublyLinkedList insertData(int key, String value) {

        DoublyLinkedList newNode = new DoublyLinkedList(key, value);

        if(start == null && end == null) {
            start = newNode;
            end = newNode;
           /* newNode.previous = null;
            newNode.next = null;*/
        } else {

            start.previous = newNode;
            newNode.next = start;
            //newNode.previous = null;
            start = newNode;
        }

        return newNode;
    }


}

class DoublyLinkedList {
     Integer key;
     String value;
     DoublyLinkedList previous;
     DoublyLinkedList next;

    public DoublyLinkedList(int key, String value) {
    }


}


record Name(int data) {

}
