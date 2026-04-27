package code;

import java.util.HashSet;
import java.util.Set;

class Solution {


    public static void main(String[] args) {
         Solution solution = new Solution();
        int answer = solution.longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1});

        System.out.println(answer);
      //  System.out.println(answer);
        /*for(int[] val : answer) {
            System.out.println(val[0] +" " +val[1]);
        }*/
    }

    public int longestConsecutive(int[] nums) {

        Set<Integer> sets = new HashSet<>();
        for (int num : nums) {
            sets.add(num);
        }

        int maxLength = 0;

        for (int val : sets ) {

            if(!sets.contains(val -1)) {
                int length = 1;
                int currentVal = val;

                while (sets.contains(currentVal + 1)) {
                    length++;
                    currentVal++;
                }

                maxLength = Math.max(maxLength, length);
            }
        }

        return maxLength;
    }

}