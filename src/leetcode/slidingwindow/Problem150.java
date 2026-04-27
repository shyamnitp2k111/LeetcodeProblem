package leetcode.slidingwindow;

import java.util.*;

public class Problem150 {

    public static void main(String[] args) {

       // int[] results = topKFrequent(new int[]{1,2},2);
       // Arrays.stream(results).forEach(System.out::println);

        Problem150 problem150 = new Problem150();
        List<String> strs = new ArrayList<>();
        strs.add("");
        //strs.add("Sita");
        String encoded = problem150.encode(strs);

        System.out.println(encoded);

        problem150.decode(encoded).stream().forEach(System.out::println);
    }

    public String encode(List<String> strs) {

        StringBuilder encoderMessages = new StringBuilder();

        for(String str : strs) {
            encoderMessages.append(str).append("#");
        }
        return encoderMessages.toString();
    }


    public List<String> decode(String str) {
        if(str.indexOf(0) =='#') {
             List<String> ret = new ArrayList<>();
             ret.add("");
        }
        return Arrays.stream(str.split("#")).toList();
    }


}
