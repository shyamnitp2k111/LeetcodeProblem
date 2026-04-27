package monostack;

import java.util.Arrays;
import java.util.Stack;

class Solution1 {

    public static void main(String[] args) {
        Solution1 solution = new Solution1();
       // Arrays.stream(solution.find132pattern(new int[]{1,2,3,4,3})).forEach(System.out::println);

        System.out.println(solution.find132pattern(new int[]{3,5,0,3,4}));
    }
    public boolean find132pattern(int[] nums) {

        Stack<Integer> stack = new Stack<>();
        int third = Integer.MIN_VALUE;

        for (int i = nums.length - 1; i >= 0; i--) {

            if (nums[i] < third) {
                return true;
            }

            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                third = stack.pop();
            }

            stack.push(nums[i]);
        }

        return false;
    }
}