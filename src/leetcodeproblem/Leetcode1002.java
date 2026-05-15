package leetcodeproblem;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Leetcode1002 {

    public static void main(String[] args) {
        Leetcode1002 leetcode1002 = new Leetcode1002();
        List<String> answer = leetcode1002.commonChars(new String[]{"bella","label","roller"});

        answer.forEach(System.out::println);
    }


    public List<String> commonChars(String[] words) {
        List<String> list = new ArrayList<>();
        Map<Character, Integer> map = new ConcurrentHashMap<>();

        String word = words[0];

        for (int index = 0; index < word.length(); index++) {
            char ch = word.charAt(index);
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }

        for(int index = 1; index < words.length; index++){

            word = words[index];

            for(Map.Entry<Character, Integer> entry : map.entrySet()) {
                if(!word.contains(entry.getKey().toString())) {
                    map.remove(entry.getKey());
                } else {
                    long count = word.chars().filter(ch -> ch == entry.getKey()).count();
                    map.put(entry.getKey(), (int) Math.min(entry.getValue(), count));
                }
            }
        }

        for(Map.Entry<Character, Integer> entry : map.entrySet()) {
            int val = entry.getValue();

            while (val > 0) {
                list.add(String.valueOf(entry.getKey()));
                val--;
            }
        }
        return list;
    }
}
