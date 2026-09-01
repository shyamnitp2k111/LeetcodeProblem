package company.startup;

import java.util.HashSet;
import java.util.Set;

public class UniqueLengthSubString {

    //abcabcbb
    // 3

    //length -> largest unique substring

    static void main() {
        String str = "abcabcbb";

        int val = findLength(str);
        System.out.println(val);
    }

    private static int findLength(String str) {

        int left = 0;
        int right = 0;
        int length = 1;
        Set<Character> set = new HashSet<>();


        while(right < str.length() && left <= right) {
            int currentLength = 0;
            while (right < str.length() && set.add(str.charAt(right)) ) {
                currentLength++;
                right++;
            }

            length = Math.max(length, currentLength);

            while (left < str.length() && left <= right && set.contains(str.charAt(left))) {
                set.remove(str.charAt(left));
                left++;
            }
        }

        return length;
    }


}
