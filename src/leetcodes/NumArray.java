package leetcodes;

import java.util.Arrays;

class NumArray {

    int[] prefix ;
    public NumArray(int[] nums) {
        prefix = new int[nums.length];
        if(nums.length > 0) {
            prefix[0] = nums[0];
        }

        for(int index = 1; index < nums.length ; index++) {
            prefix[index] = prefix[index -1 ] + nums[index];
        }

        Arrays.stream(prefix).forEach(System.out::println);
    }
    
    public int sumRange(int left, int right) {
        if(left -1 <= 0) {
            return prefix[right];
        }
        return prefix[right] - prefix[left -1];
    }


    public static void main(String[] args) {
        NumArray numArray = new NumArray(new int[]{-2,0,3,-5,2,-1});
        System.out.println("answer is "+ numArray.sumRange(0, 5));
    }
}