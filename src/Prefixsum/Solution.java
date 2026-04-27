package Prefixsum;

import java.util.*;
import java.util.stream.Collectors;

class Solution {

    int val = 0;
    public static void main(String[] args) {
        Solution solution = new Solution();
       // solution.findMissingElements(new int[]{5,1}).forEach(System.out::println);

        //Arrays.stream(solution.buildArray(new int[]{0, 2, 1, 5, 3, 4})).forEach(System.out::println);
       // Arrays.stream(solution.findWordsContaining(new int[]{1, 2, 1})).forEach(System.out::println);


        solution.alternatingSum(new int[]{1,3,5,7});
        /*Random random = new Random();
        int generateNumberForRectangle = random.nextInt(10);
        System.out.println(generateNumberForRectangle);*/
    }

    public int alternatingSum(int[] nums) {

        int length = nums.length;
        int sum = 0;
        for(int index = 0 ; index < length ; index ++) {
            sum = index % 2 == 0 ? sum + nums[index] : sum - nums[index];
        }

        return sum;
    }


}