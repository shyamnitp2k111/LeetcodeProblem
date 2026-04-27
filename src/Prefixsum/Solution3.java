package Prefixsum;

import java.util.*;
import java.util.stream.Collectors;

class Solution3 {
    public static void main(String[] args) {
        //System.out.println(Math.toIntExact(-2));
        Solution3 solution3 = new Solution3();
        System.out.println(solution3.countPairs(List.of(-1,1,2,3,1), 2));
       // System.out.println(solution3.numberGame(new int[]{25,64,9,4,100}));

        //Arrays.stream(solution3.findXSum(new int[]{1,1,2,2,3,4,2,3},6,2)).forEach(System.out::println);
    }


    public int countPairs(List<Integer> nums, int target) {
        int length = nums.size();
        Collections.sort(nums);
        int count = 0;

        int leftPoint = 0 ;
        int rightPoint = length -1;

        while (leftPoint <= rightPoint) {

            if(nums.get(leftPoint) + nums.get(rightPoint) <= target) {
                count++;
                leftPoint++;
            } else {
                rightPoint--;
            }
        }
        return count;
    }
}