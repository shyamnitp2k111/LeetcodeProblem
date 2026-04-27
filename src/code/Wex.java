package code;

import java.lang.reflect.Array;
import java.util.*;

public class Wex {

    public static void main(String[] args) {
        ///*
        //Given an array of words arr[], the task is to groups strings that are anagrams.
        //An anagram is a word or phrase formed by rearranging the letters of another, using all the original letters exactly once.
        //
        //Input: arr[] = ["listen", "silent", "enlist", "abc", "cab", "bac", "rat", "tar", "art"]
        //Output: [["abc", "cab", "bac"], ["listen", "silent", "enlist"],["rat", "tar", "art"]]
        //Explanation:
        //Group 1: "abc", "bac" and "cab" are anagrams.
        //Group 2: "listen", "silent" and "enlist" are anagrams.
        //Group 3: "rat", "tar" and "art" are anagrams.
        //*/

        String[] arr = {"listen", "silent", "enlist", "abc", "cab", "bac", "rat", "tar", "art"};


        Map<String, List<String>> map = new HashMap<>();

        for(int index = 0 ; index < arr.length; index++) {
            String word = arr[index];
            char [] ch = word.toCharArray();

            Arrays.sort(ch);

            if(!map.containsKey(new String(ch))) {
                List<String> listOfWord = new ArrayList<>();
                listOfWord.add(word);
                map.put(new String(ch),listOfWord);
            } else {
                List<String> listOfWords = map.get(new String(ch));
                listOfWords.add(word);
                map.put(new String(ch),listOfWords);
            }
        }


        for(Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getValue());
        }




/*
        In our automated testing suite, we need a utility that flags transaction IDs based on their divisibility for routing purposes.
        If an ID is divisible by 3, mark as 'Fuel'; by 5, mark as 'Service'; by both, mark as 'WEX'."
        Technical Requirement:
        Return a list of strings representing the mapping. While simple, look for readability and avoiding redundant modulo operations.
*/




        List<String> listOfTransactionId = new ArrayList<>();
        listOfTransactionId.add("2");
        listOfTransactionId.add("3");
        listOfTransactionId.add("4");
        listOfTransactionId.add("5");
        listOfTransactionId.add("6");


        List<String> resultList = new ArrayList<>();

        for(int index = 0 ; index < listOfTransactionId.size(); index++) {


            if(Integer.parseInt(listOfTransactionId.get(index)) % 3 == 0 && Integer.parseInt(listOfTransactionId.get(index)) % 5 == 0){
                resultList.add("WEX");
                continue;
            } else if(Integer.parseInt(listOfTransactionId.get(index)) % 3 == 0) {
                resultList.add("Fuel");
                continue;
            } else if(Integer.parseInt(listOfTransactionId.get(index)) % 5 == 0) {
                resultList.add("Service");
                continue;
            }

            resultList.add(listOfTransactionId.get(index));
        }





    }
}
