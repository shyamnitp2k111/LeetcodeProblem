package company.interview;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentModificationExample {

    public static void main(String[] args) {

        //Concurrent modification exception - HashMap
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("A", 1);
        map1.put("B", 2);
        for(String key : map1.keySet()) {
            map1.remove(key);
        }

        //Concurrent modification exception - HashMap
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        for(String key : map.keySet()) {
            map.remove(key);
        }
    }

}