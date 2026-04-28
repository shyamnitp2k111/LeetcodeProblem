package leetcode.slidingwindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution3 {

    public static void main(String[] args) {
        Solution3 solution = new Solution3();
        List<Integer> result = solution.partitionLabels("caedbdedda");

        System.out.println(result);
    }

    public List<Integer> partitionLabels(String s) {

        Map<Character, Integer> map = new HashMap<>();

        for(int index = s.length()-1 ; index >= 0; index--) {
            if(!map.containsKey(s.charAt(index))) {
                map.put(s.charAt(index), index);
            }
        }

        List<Integer> results = new ArrayList<>();

        int lastIndex = -1;
        int prev = -1;
        for(int index = 0 ; index< s.length(); index++) {

            lastIndex = Math.max(map.get(s.charAt(index)), lastIndex);
            if(index == lastIndex) {
                if(prev == -1) {
                    results.add(index + 1);
                } else {
                    results.add(index - prev);
                }
                prev = index;
                lastIndex = -1;
            }
        }

        return results;
    }
}