package leetcode.slidingwindow;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class queeee {

    public static void main(String[] args) {
        Queue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());

        queue.add(1);
        queue.add(2);
        queue.add(1);
        queue.add(1);

        System.out.println(queue.poll());

        System.out.println(queue);

    }
}
