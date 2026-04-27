package code;

import java.util.HashMap;
import java.util.Map;

public class Question {


    //Given two strings s and t, return the number of distinct subsequences of s which equals t.
    //
    //
    //
    //Input: s = "rabbbit", t = "rabbit"  Output: 3
    //Explanation:
    //As shown below, there are 3 ways you can generate "rabbit" from s.
    //rabbbit
    //rabbbit
    //rabbbit


    // docker compose
    public static void main(String[] args) {

        String s = "rabbbit";
        String t = "rabbit";

        Map<Character, Integer > map = new HashMap<>();

        for(int index = 0 ; index < s.length(); index++) {
            map.put(s.charAt(index), map.getOrDefault(s.charAt(index) , 0) +1);
        }

        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        System.out.println(helperMethod(map, s, t , stringBuilder, count, 0));
    }

    private static int helperMethod(Map<Character, Integer> map, String s, String t, StringBuilder stringBuilder, int count, int index) {

        if(stringBuilder.toString().equals(t)) {
            return ++count;
        }

        if(index == s.length() -1) {
            return count;
        }

        if(map.containsKey(s.charAt(index))) {

            helperMethod(map,s, t, stringBuilder.append(s.charAt(index)), count, ++index);
            int val = map.get(s.charAt(index));

            if(val == 1) {
                helperMethod(map,s, t, stringBuilder, count, ++index);
                map.remove(s.charAt(index));
            }

        }

        return count;
    }


}
