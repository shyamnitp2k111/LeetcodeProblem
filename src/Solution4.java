import java.util.*;

class Solution4 {

    public static void main(String[] args) {
        Solution4 solution4 = new Solution4();
        solution4.nextPermutation(new int[]{1,2,3});
    }


    public void nextPermutation(int[] nums) {

        int in = -1;
        for(int index = nums.length - 2; index >=0 ; index--) {
            if(nums[index] < nums[index+1]){
                in = index;
                break;
            }
        }


        // 2. if no pivot → reverse whole array
        if (in == -1) {
            reverse(nums, 0, nums.length - 1);
            return;
        }


        // 3. find smallest greater element from right
        int secondIndex = -1;
        for (int index = nums.length - 1; index > in; index--) {
            if (nums[index] > nums[in]) {
                secondIndex = index;
                break;
            }
        }


        if(secondIndex == -1) return;

        int temp = nums[in];
        nums[in] = nums[secondIndex];
        nums[secondIndex] = temp;


        Arrays.sort(nums, in + 1, nums.length -1);

        Arrays.stream(nums).forEach(System.out::println);
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}