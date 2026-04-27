package code;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstring {


    /*Examples
    Input:  "abcabcbb"
    Output: 3*/
    public static void main(String[] args) {

        /*//abba
        //aab
        leftpointer -> 0
        rightPointer -> 0
              maxLength -> 1


                      set -> a
*/
        String str = "abcabcbb";

        // set
        Set<Character> set = new HashSet<>();

        int leftPointer = 0;
        int rightPointer = 0;
        int maxLength = 1;

        while(rightPointer < str.length()) {

            while (set.contains(str.charAt(rightPointer))) {
                set.remove(str.charAt(leftPointer));
                leftPointer++;
            }

            set.add(str.charAt(rightPointer));
            maxLength = Math.max(maxLength, rightPointer -leftPointer+ 1);
            rightPointer++;
        }

        System.out.println( maxLength);


    }
}
