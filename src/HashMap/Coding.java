package HashMap;

import java.util.Arrays;

public class Coding {

    public static void main(String[] args) {

        Coding coding = new Coding();
        System.out.println(coding.isAlienSorted(new String[]{"hello","leetcode"}, "hlabcdefgijkmnopqrstuvwxyz"));
    }


    public boolean isAlienSorted(String[] words, String order) {

        char[] orderList = order.toCharArray();

        int MAX_LENGTH = Arrays.stream(words).mapToInt(String::length).max().orElse(0);
        int MIN_LENGTH = Arrays.stream(words).mapToInt(String::length).min().orElse(0);

        System.out.println(MAX_LENGTH);

        int startingIndex = 0;

        for(int index = 0 ; index < MAX_LENGTH ; index++ ) {
            for(String word : words) {
                if(index < word.length() ) {

                }
            }
        }
        return false;
    }
}
