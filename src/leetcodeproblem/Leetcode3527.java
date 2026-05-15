package leetcodeproblem;

import java.util.*;

public class Leetcode3527 {

    public static void main(String[] args) {
        Leetcode3527 leetcode3527 = new Leetcode3527();

        List<List<String>> responses = new ArrayList<>();
        List<String> list = List.of("good","ok","good","ok");
        responses.add(list);
        list = List.of("ok","bad","good","ok","ok");
        responses.add(list);

        List<String> list2 = List.of("good");
        responses.add(list2);
        List<String> list3 = List.of("bad");
        responses.add(list3);

        String results = leetcode3527.findCommonResponse(responses);

        System.out.println("results is "+ results);
    }


    public String findCommonResponse(List<List<String>> responses) {

        Map<String, Integer> map = new TreeMap<>();
        for(List<String> response : responses) {
            Set<String> set = new HashSet<>(response);

            for(String str : set) {
                map.put(str, map.getOrDefault(str, 0) + 1);
            }
        }

        String answer = null;
        int maxValue = Integer.MIN_VALUE;

        for( Map.Entry<String, Integer> entry : map.entrySet()) {
            if( entry.getValue() > maxValue ) {
                maxValue = entry.getValue();
                answer = entry.getKey();
            }
        }
        return answer;
    }
}
