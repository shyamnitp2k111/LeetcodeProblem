package monostack;

import java.util.Arrays;
import java.util.Stack;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        Arrays.stream(solution.canSeePersonsCount(new int[]{10,6,8,5,11,9})).forEach(System.out::println);
        System.out.println("answer is ..."+ solution.minSubArrayLen(11, new int[]{1,2,3,4,5}));
    }
    public int[] canSeePersonsCount(int[] heights) {

        int n = heights.length;
        int[] result = new int[n];
        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {

            int count = 0;

            while (!stack.isEmpty() && heights[i] > stack.peek()) {
                stack.pop();
                count++;
            }

            if (!stack.isEmpty()) {
                count++; // can see the first taller person
            }

            result[i] = count;
            stack.push(heights[i]);
        }

        return result;
    }


    public int minSubArrayLen(int target, int[] nums) {

        int left = 0 ;
        int right = 0;
        int result = Integer.MAX_VALUE;

        int sum = nums[0];
        while(left <= right && right < nums.length -1) {

            if(nums[left] == target || nums[right] == target) {
                return 1;
            }
            if(sum == target) {
                result = Math.min(result, right - left);
                left++;
                right = left + 1;
                sum = nums[left] + nums[right];
            } else if(sum > target){
                left++ ;
                right = left + 1;
                sum = nums[left] + nums[right];;
            } else {
                right++;
                sum += nums[right];
            }
        }

        return result == Integer.MAX_VALUE ? 0 : result;
    }
}