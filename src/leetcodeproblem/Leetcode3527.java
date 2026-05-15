package leetcodeproblem;

import java.util.Arrays;

public class Leetcode905 {

    public static void main(String[] args) {
        Leetcode905 leetcode905 = new Leetcode905();
        int[] results = leetcode905.sortArrayByParity(new int[]{3,1,2,4});

        Arrays.stream(results).forEach(System.out::println);
    }


    public int[] sortArrayByParity(int[] nums) {

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            if(nums[left] % 2 != 0 && nums[right] % 2 == 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            } else if(nums[left] % 2 == 0) {
                left++;
            } else if(nums[right] % 2 != 0) {
                right--;
            } else {
                left++;
                right--;
            }
        }

        return nums;
    }
}
