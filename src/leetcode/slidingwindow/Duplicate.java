package leetcode.slidingwindow;


public class Duplicate {

    public static void main(String[] args) {

        int[] nums = {1,2,3,1};
        int results = findLHS(nums);
        System.out.println(results);
    }

    public static int findLHS(int[] nums) {

        if(nums.length<=1){
            return 0;
        }

        int left = 0;
        int right = 1;

        int min = Math.min(nums[left], nums[right]);
        int max = Math.max(nums[left], nums[right]);

        int lengthOfSubArray = 0;

        int diff;

        while (left < right && right < nums.length) {

            diff = Math.abs(nums[left] - nums[right]);

            if( diff > 1) {
                left = right;
                right ++;
            } else if( diff == 1 ) {
                right ++;

                while ( right < nums.length) {
                    if(nums[right] >=min && nums[right]  <= max) {
                        lengthOfSubArray++;
                        right++;
                    } else {
                        left = right;
                        right++;
                        break;
                    }
                }
            }
        }
        return lengthOfSubArray;
    }
}
