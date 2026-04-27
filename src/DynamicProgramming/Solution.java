package DynamicProgramming;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxScoreWords(new String[]{"dog","cat","dad","good"},
                new char[]{'a','a','c','d','d','d','g','o','o'},
                new int[]{1,0,9,5,0,0,3,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0}));
    }

    public int maxScoreWords(String[] words, char[] letters, int[] score) {

          Arrays.sort(letters);
          for(String word : words) {
              char[] ch = word.toCharArray();
              Arrays.sort(ch);

          }

          return 0;

    }
}
