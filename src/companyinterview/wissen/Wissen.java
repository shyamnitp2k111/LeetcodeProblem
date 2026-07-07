package companyinterview.wissen;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class Wissen {

    public static void main(String[] args) {

        int [] arr = {50 ,60, 100, 50, 80};

        int[] result = new int[arr.length];
        Queue<int[]> queue = new PriorityQueue<>((a,b) -> a[0] - b[0]);


        for(int index = 0 ; index < arr.length; index++) {
            queue.offer(new int[]{arr[index], index});
        }

        int rank = 1;
        int prev = -1;

        while (!queue.isEmpty()) {
            int[] pollVal = queue.poll();

            if(prev == pollVal[0] || prev == -1) {
                result[pollVal[1]] = rank;
                prev = pollVal[0];
            } else  {
                result[pollVal[1]] = ++rank;
                prev = pollVal[0];
            }
        }

        Arrays.stream(result).forEach(System.out::println);

    }
}
