package leetcode.slidingwindow;

import java.util.*;

class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
       // Arrays.stream(solution.countGoodSubstrings(new int[]{2,4,9,3}, -2)).forEach(System.out::println);
        System.out.println(solution.countGoodSubstrings("aababcabc"));
    }

    public int countGoodSubstrings(String s) {

        Set<Character> set = new HashSet<>();
        int count = 0;

        for(int index = 0 ; index < s.length() ; index++) {
            if(!set.add(s.charAt(index))) {
                set = new HashSet<>();
                set.add(s.charAt(index));
            } else {
                if(set.size() == 3 ) {
                    count++;
                    set = new HashSet<>();
                }
            }
        }
        Queue<Integer> queue = new LinkedList<>();

        queue.add(4);
        queue.add(2);
        queue.add(3);

        queue.remove(3);
        queue.remove(2);

        System.out.println("remove : "+ queue.poll());
        System.out.println("remove : "+ queue.poll());
        queue.add(3);
        System.out.println("remove : "+ queue.poll());
        System.out.println("remove : "+ queue.poll());


        return count;




       /* for(int index = 0 ; index < 3; index++) {
            set.add(s.charAt(index));
        }

        if(set.size() == 3) {
            count++;
        }

        System.out.println(set);

        for(int index = 3 ; index < s.length() ; index++) {
           set.remove(s.charAt(index - 3));
           set.add(s.charAt(index));

           System.out.println(set);
           if(set.size() == 3) {
               count++;
           }
        }
        return count;

        */



    }
}