package company;

import java.util.HashSet;
import java.util.Set;

public class coding {

    static void main() {
        //Find the longest substring without repeating chars
        //
        //Input: s = "zxyzxyz"
        //
        //
        //
        //Output: 3
        //
        //
        //
        //Input: s = "xxxx"
        //
        //
        //
        //Output: 1

        String str = "zxzxz";

        if(str.isEmpty()) {
            return;
        }
        int left = 0;
        Set<Character> set = new HashSet<>();
        int maxLength = 1;
        set.add(str.charAt(left));

        for(int right = 1; right < str.length(); right++) {

            // compression
            while(left <= right && set.contains(str.charAt(right))) {
                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);

            set.add(str.charAt(right));
        }

        System.out.println(maxLength);
    }
}
