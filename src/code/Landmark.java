package code;

import java.util.*;
import java.util.stream.Collectors;

public class Landmark {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();


        //1,1,2,2,3,3,3,4,4,4,4
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);

        Map<Integer, Long> map = list.stream().
                collect(Collectors.groupingBy(i -> i, Collectors.counting()));

        for(Map.Entry<Integer, Long> entries : map.entrySet()) {
            System.out.println(entries.getKey() + " "+ entries.getValue());
        }


        System.out.println("-----------------------------------------");
        map = map.entrySet().stream().sorted((i, j) ->
                j.getKey() - i.getKey()).skip(map.size() -3).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (a,b) -> a));

        for(Map.Entry<Integer, Long> entries : map.entrySet()) {
            System.out.println(entries.getKey() + " "+ entries.getValue());
        }

        HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
        //// Adding Key and Value pairs to HashMap
        hashmap.put(22, "A");
        hashmap.put(55, "B");
        hashmap.put(33, "Z");
        hashmap.put(44, "M");
        hashmap.put(99, "I");
        hashmap.put(88, "X");
        //
        ////Sort this hash map based on keys in descending order*/


        Map<Integer, String> hashmap1 = hashmap.entrySet().stream().sorted( (i, j) ->
                   j.getKey() - i.getKey()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a,b) ->a, LinkedHashMap::new));


        System.out.println("---------------------------------------");
        for(Map.Entry<Integer, String> mapentry : hashmap1.entrySet() ) {
            System.out.println(mapentry.getKey() +" "+ mapentry.getValue());
        }

    }
}