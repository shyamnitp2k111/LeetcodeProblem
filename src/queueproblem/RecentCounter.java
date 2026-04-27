package queueproblem;

import java.util.ArrayDeque;
import java.util.Queue;

class Solution {

    public static void main(String[] args) {

        Solution solution = new Solution();
        System.out.println(solution.timeRequiredToBuy(new int[]{2,3,2}, 2));
    }

    public int timeRequiredToBuy(int[] tickets, int k) {

        Queue<Integer> queue = new ArrayDeque<>();

        for(int index = 0 ; index < tickets.length; index++) {
            queue.add(index);
        }

        int time = 0 ;

        while(!queue.isEmpty() && tickets[k] != 0) {
            int index = queue.poll();
            tickets[index] = tickets[index] - 1;
            if(tickets[index] != 0) {
                queue.add(index);
            }
            time++;
        }
        return time;
    }
}