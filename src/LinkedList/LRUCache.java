package LinkedList;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    Map<Integer, DoublyLinkedListNode> map;
    DoublyLinkedListNode head;
    DoublyLinkedListNode tail;
    int capacity ;
    int size;
    public LRUCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        map = new HashMap<>();
        head = new DoublyLinkedListNode(-1, null, null);
        tail = new DoublyLinkedListNode(-1, head, null);
        head.setNext(tail);
    }
    
    public int get(int key) {
        if(!map.containsKey(key)) {
            return -1;
        } else {
            DoublyLinkedListNode value = map.get(key);
            value.prev.next = value.next;
            value.next.prev = value.prev;

            value.next = head.next;
            value.prev = head;
            return value.getData();
        }

    }
    
    public void put(int key, int value) {

        if(map.containsKey(key)) {
            DoublyLinkedListNode val = map.get(key);
            val.setData(value);
            map.put(key,val);
        } else {
            if(size == 0) {
                DoublyLinkedListNode newNode = new DoublyLinkedListNode(value, head, tail);
                head.next = newNode;
                tail.prev = newNode;
                map.put(key, newNode);
                size++;
            } else if(size < capacity){
                DoublyLinkedListNode newNode = new DoublyLinkedListNode(value, head, head.next);
                head.next = newNode;
                newNode.next.prev = newNode;
                size++;
                map.put(key, newNode);
            } else {
                map.remove(key);
                DoublyLinkedListNode temp = tail.prev.prev;
                temp.next = tail;
                tail.prev = temp;
               // map.remove(removeNode.data);
                /*removeNode.next = null;
                removeNode.prev = null;
                map.remove(removeNode.data);*/

                // add element into begin
                DoublyLinkedListNode newNode = new DoublyLinkedListNode(value, head, head.next);
                head.next = newNode;
                newNode.next.prev = newNode;
                map.put(key, newNode);
            }
        }
    }


    public static void main(String[] args) {
        LRUCache obj = new LRUCache(3);
        obj.put(1,2);
        obj.put(2,20);
        obj.put(3,200);
        System.out.println(obj.get(3));
        System.out.println(obj.get(1));

        obj.put(4, 2000);

        System.out.println(obj.get(1));
        System.out.println(obj.get(2));
        System.out.println(obj.get(3));
        System.out.println(obj.get(4));
    }
}

class DoublyLinkedListNode{
    int data;
    DoublyLinkedListNode prev;
    DoublyLinkedListNode next;

    public DoublyLinkedListNode(int data) {
        this.data = data;
    }

    public DoublyLinkedListNode(int data, DoublyLinkedListNode prev, DoublyLinkedListNode next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public DoublyLinkedListNode getPrev() {
        return prev;
    }

    public void setPrev(DoublyLinkedListNode prev) {
        this.prev = prev;
    }

    public DoublyLinkedListNode getNext() {
        return next;
    }

    public void setNext(DoublyLinkedListNode next) {
        this.next = next;
    }
}

