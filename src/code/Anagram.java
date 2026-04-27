package code;

import java.util.HashMap;
import java.util.Map;

public class Anagram {

    //paytm
    /*Question 1:
    Given two strings s and t, return true if s is an anagram of t. Return false otherwise.*/

    public static void main(String[] args) {
        System.out.println(anagram("anagram","nagaramh"));
    }

    private static boolean anagram(String s, String t) {

       /* char[] sArray = s.toCharArray();
        char[] tArray = t.toCharArray();

        Arrays.sort(sArray);
        Arrays.sort(tArray);


        return new String(sArray).equals(new String(tArray));*/


        Map<Character, Integer> map = new HashMap<>();

        for(int index = 0 ; index < s.length(); index++) {
            map.put(s.charAt(index), map.getOrDefault(s.charAt(index), 0) + 1);
        }


        for(int index = 0 ; index < t.length(); index++) {
            if(map.containsKey(t.charAt(index))) {
                int val = map.get(t.charAt(index));
                if(val > 0 ) {
                    map.put(t.charAt(index), map.get(t.charAt(index)) - 1);
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }


        for(Map.Entry<Character, Integer> entries : map.entrySet()) {
            if(entries.getValue() !=0) {
                return false;
            }
        }


        return true;

    }
}
