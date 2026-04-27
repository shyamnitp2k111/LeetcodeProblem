package queueproblem;

class MyCircularQueue {

    int[] array;
    int front ;
    int rear ;
    int length ;
    public MyCircularQueue(int k) {
        array = new int[k];
        front = -1;
        rear = -1;
        length = k;
    }
    
    public boolean enQueue(int value) {
        if ((rear + 1) % length == front) {
            return false;
        } else {
            if(rear == length ) {
                array[0] = value;
                rear = 0;
            } else {
                array[rear++] = value;
            }
            return true;
        }
    }
    
    public boolean deQueue() {
        if(front == -1 && rear == -1) {
            return false;
        } else {
            if(rear == 0) {
                rear = length -1 ;
            } else {
                rear--;
            }
            return true;
        }
    }
    
    public int Front() {

        if(front != -1) {
            return array[front];
        }

        return -1;
    }
    
    public int Rear() {
        if(rear != -1 ) {
            return array[rear];
        }

        return -1;
    }
    
    public boolean isEmpty() {
        return front == -1 && rear == -1;
    }
    
    public boolean isFull() {
        return (rear + 1) % length == front;
    }
}