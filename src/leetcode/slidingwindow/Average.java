package leetcode.slidingwindow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Average {

    public static void main(String[] args) {
        //int[] nums = {0,4,0,3,2};
        String input = "YazaAay";
        String results = longestNiceSubstring(input);
        System.out.println(results);
    }


    public static String longestNiceSubstring(String input) {

        Set<Character> set = new HashSet<>();

        StringBuilder result = new StringBuilder();

        for(int index = 0 ; index < 2; index++) {
            set.add(input.charAt(index));
        }

        if(set.size() == 2) {
            List<Character> list = set.stream().toList();

            if(Math.abs(list.get(0) - list.get(1)) == 32){
                result.append(list.get(0)).append(list.get(1));
            }
        }
        //left = index - k + 1
        //right = k

        int k = 2;

        for(int index = k ; index < input.length() ; index++) {
            set.add(input.charAt(index));

            if(set.size() == 2 && checkNiceString(set)) {
                result.append(input.charAt(index));
            } else {
                set.remove(input.charAt(index - k));
            }
        }

        return result.toString();
    }

    private static boolean checkNiceString( Set<Character> set) {
        if(set.size() == 2) {
            List<Character> list = set.stream().toList();

            return Math.abs(list.get(0) - list.get(1)) == 32;
        }
        return false;
    }
}
