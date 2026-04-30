import java.util.*;

class Solution4 {

    public static void main(String[] args) {
        Solution4 solution4 = new Solution4();
        solution4.sortColors(new int[]{2,0,2,1,1,0});
    }


    public void sortColors(int[] nums) {
        int[] bucket = new int[3];


        for (int num : nums) {
            bucket[num]++;
        }

        int in = 0;
        for(int index = 0; index < bucket.length; index++) {
            int val = bucket[index];

            while (val > 0) {
                nums[in++] = index;
                val--;
            }
        }


        for(int index = 0 ; index < nums.length; index++) {
            System.out.println(nums[index]);
        }
    }
}