package leetcode.slidingwindow;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

class KthLargest {

    private Queue<Integer> queue;
    int topK ;
    public KthLargest(int k, int[] nums) {
        queue = new PriorityQueue<>(k, Comparator.naturalOrder());
        topK = k;
        for(int number : nums) {
            queue.add(number);

            if(queue.size() > k) {
                queue.poll();
            }
        }
    }
    
    public int add(int val) {
        queue.add(val);

        if(queue.size() > topK) {
            queue.poll();
        }
        return queue.isEmpty() ? -1 : queue.peek();
    }
}