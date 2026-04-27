package views;

import java.util.Comparator;
import java.util.PriorityQueue;

public class qqqqqqq {

    public static void main(String[] args) {


        int [] array = {1,3,5,2};
        PriorityQueue<Integer> priorityQueues = new PriorityQueue<>( Comparator.naturalOrder());


        for( int nums : array) {
            priorityQueues.offer(nums);
        }


       /* Arrays.stream(array).forEach(System.out::println);*/


        System.out.println(priorityQueues.size());

        while(!priorityQueues.isEmpty() ) {
            //System.out.println(priorityQueues.size());
            System.out.println(priorityQueues.poll());
        }

        System.out.println(priorityQueues.size());
    }

}
