package leetcodes.sol;

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
       // System.out.println(solution.nextGreaterElements(new int[]{1,2,3,4,4,3,2,1}));

        //solution.nextGreaterElements(new int[]{1,2,3,4,3});

        //Input: nums = [1,2,3,4,3]
        //Output: [2,3,4,-1,4]
       // Arrays.stream(solution.insert(new int[][]{{1,3},{6,9}}, new int[]{2,5})).forEach(System.out::println);
        int answer = solution.eraseOverlapIntervals(new int[][]{{1,3},{6,9}});
        System.out.println(answer);
       /* for(int index = 0 ; index < answer.length ; index++) {
            System.out.println("interval is "+ answer[index][0] + " and "+ answer[index][1]);
        }*/
    }

    public int eraseOverlapIntervals(int[][] intervals) {

        int length = intervals.length;
        Arrays.sort(intervals , (a,b) -> a[1] - b[1]);

        int count = 1;

        int previous = 0;

        for(int index = 1; index < length; index++) {

            if(intervals[previous][1] < intervals[index][0]) {
                count++;
                previous = index;
            }
        }

        return length - count;
    }


}