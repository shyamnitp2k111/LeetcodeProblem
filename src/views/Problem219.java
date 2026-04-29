package views;

import java.util.HashSet;
import java.util.Set;

class Duplicate {

    public static void main(String[] args) {

        int[] nums = {1,2,3,1};
        boolean results = containsNearbyDuplicate(nums, 3);
        System.out.println(results);
    }

    public static boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<>();

        for(int index = 0 ; index <= k && index <= nums.length; index++) {
            if(!set.add(nums[index])){
                return true;
            }
        }
        int left = 0;
        int right = k+1;
        while (right < nums.length) {
            set.remove(nums[left]);
            if(!set.add(nums[right])){
                return true;
            }
            right +=1;
            left +=1;
        }

        return false;
    }
}
