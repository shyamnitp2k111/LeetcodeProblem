package HashMap;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Duplicate {

    public static void main(String[] args) {
        String result = String.join("", Collections.nCopies(257, "X"));
        System.out.println(result);
        int[] results = new Duplicate().findErrorNums(new int[]{1, 2, 2, 4});
        Arrays.stream(results).forEach(System.out::println);
    }

    public int[] findErrorNums(int[] nums) {

        int lengthOfArray = nums.length;
        int[] results = new int[2];

        Set<Integer> set = new HashSet<>();
        int sum = 0;

        for(int index = 0 ; index < lengthOfArray ; index++) {
            if(set.add(nums[index])) {
                sum += nums[index];
            } else {
                results[0] = nums[index];
            }
        }

        int sumOfN = (lengthOfArray * (lengthOfArray +1))/2;
        results[1] = sumOfN - sum;

        return results;
    }
}
