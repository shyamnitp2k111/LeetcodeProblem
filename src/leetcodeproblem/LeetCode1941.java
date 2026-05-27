package leetcodeproblem;


public class LeetCode1941 {

    public static void main(String[] args) {
        System.out.println(areOccurrencesEqual("abacbca"));
    }

    public static boolean areOccurrencesEqual(String s) {
        int[] freq = new int[26];

        for(int index = 0 ; index < s.length(); index++) {
            char ch = s.charAt(index);
            freq[ch - 'a']++;
        }

        int val = -1;
        for (int i : freq) {
            if (i != 0) {
                if (val == -1) {
                    val = i;
                } else {
                    if (val != i) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
