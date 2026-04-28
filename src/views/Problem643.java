class Solution {
    public double findMaxAverage(int[] nums, int k) {

        double sum=0;

        for(int index = 0 ; index < k ; index++) {
            sum += nums[index];
        }
        
        double results = sum;

        for(int index = k; index < nums.length ; index++) {
            sum = sum + nums[index] - nums[index -k];
            results = Math.max(sum, results);
        }

        return results /k;
    }
}
