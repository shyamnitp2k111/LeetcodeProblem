package leetcodes;

import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();

        List<List<Integer>> list = List.of(
                List.of(5, 8),
                List.of(1, 3)
        );
        int answer = solution.maximumPopulation(new int[][]{{1950,1961},{1960,1971},{1970,1981}});
       // Arrays.stream(answer).forEach(System.out::println);
        System.out.println("answer is ..." + answer);
    }

    public int maximumPopulation(int[][] logs) {

        Queue<int[]> queue = new PriorityQueue<>((a,b) -> a[0] - b[0]);
        int smallestYear = 0;

        for(int[] array : logs) {
            queue.add(new int[]{array[0],array[1]});
        }

        int lastValue = 0;
        int count =0;
        int highPopulation =0;
        while(!queue.isEmpty()) {
            int[] arr = queue.poll();

            if(smallestYear == 0) {
                smallestYear = arr[0];
                highPopulation = 1;
            }

            if(lastValue > arr[0]) {
                count++;
                smallestYear = highPopulation < count ? arr[0] : smallestYear;
            } else {
                count =0;
            }

            lastValue = Math.max(arr[1], lastValue);
        }
        return smallestYear;
    }
}