package leetcodeproblem;

import java.util.HashSet;
import java.util.Set;

public class Leetcode2309 {

    public static void main(String[] args) {

        String s = "arRAzFif";
        Set<Character> set = new HashSet<>();
        Character answer = null;

        for(int index = 0; index < s.length(); index++) {

            char ch = s.charAt(index);
            if(set.contains(Character.toUpperCase(ch))) {
                answer = answer == null ? Character.toUpperCase(ch) :
                        Character.compare(answer, Character.toUpperCase(ch)) == 1 ?  answer : Character.toUpperCase(ch);
            }

            set.add(Character.toUpperCase(ch));
        }

        System.out.println(answer);
    }
}
