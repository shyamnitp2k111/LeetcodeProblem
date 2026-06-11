import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

    public static void main(String[] args) {

        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        for(String key : map.keySet()) {
            map.remove(key);
        }
    }

}
