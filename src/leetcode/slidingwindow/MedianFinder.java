package leetcode.slidingwindow;

import java.util.*;

class MedianFinder {

    Queue<Integer> minQueue;
    Queue<Integer> maxQueue;
    public MedianFinder() {
        minQueue =new PriorityQueue<>(Comparator.naturalOrder());
        maxQueue = new PriorityQueue<>(Comparator.reverseOrder());
    }
    
    public void addNum(int num) {
        maxQueue.add(num);

        if(Math.abs(maxQueue.size() - minQueue.size()) <= 1 ) {
            if(!minQueue.isEmpty() && maxQueue.peek() > minQueue.peek() ) {
                minQueue.add(maxQueue.poll());
            }
        } else {
            minQueue.add(maxQueue.poll());
        }
    }
    
    public double findMedian() {
        if( (maxQueue.size() + minQueue.size()) % 2 != 0 ) {
            return maxQueue.size() < minQueue.size() ? minQueue.peek() : maxQueue.peek();
        } else {
            return (double) (minQueue.peek() + maxQueue.peek()) /2;
        }
    }

    public static void main(String[] args) {
        MedianFinder obj = new MedianFinder();
        obj.addNum(3);
        obj.addNum(2);
        obj.addNum(7);
        obj.addNum(4);
        obj.addNum(5);
        obj.addNum(6);
        double param_2 = obj.findMedian();
        System.out.println(param_2);
    }
}


