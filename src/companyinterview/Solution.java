package companyinterview;

import java.util.*;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.wordBreak("leetcode", List.of("leet","code")));


    }

    public boolean wordBreak(String s, List<String> wordDict) {

        int lengthOfString = s.length();
        int maxLength = wordDict.stream().mapToInt(String::length).max().getAsInt();
        boolean[] booleanArray = new boolean[lengthOfString + 1];
        Set<String> set = new HashSet<>(wordDict);

        booleanArray[0] = true;
        for(int index = 1 ; index <= lengthOfString ; index++) {
            for(int prev = index - 1; prev >= Math.max(0,index - maxLength); prev--) {
                if (booleanArray[prev] && set.contains(s.substring(prev , index))){
                    booleanArray[index] = true;
                    break;
                }
            }
        }

        return booleanArray[lengthOfString];
    }
}