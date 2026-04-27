package HashMap;

import java.util.*;

class TimeMap {

    public static void main(String[] args) {
        TimeMap timeMap = new TimeMap();
        timeMap.set("foo", "bar", 1);
        System.out.println(timeMap.get("foo", 1));
        timeMap.set("foo", "bar2", 4);
        System.out.println(timeMap.get("foo", 5));
    }

    Map<String, Queue<Value>> map ;
    public TimeMap() {
        map = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {

        Queue<Value> queue;
        if(!map.containsKey(key)) {
            queue = new PriorityQueue<>((value1, value2) -> value1.getTimeStamp() - value2.getTimeStamp());
        } else {
            queue = map.get(key);
        }
        queue.add(new Value(value, timestamp));
        map.put(key, queue);
    }
    
    public String get(String key, int timestamp) {

        if(!map.containsKey(key)) {
            return "";
        } else {

            String results = null;
            Queue<Value> queues = map.get(key);

            int size = queues.size() ;

            List<Value> list = new ArrayList<>();
            while(size > 0) {

                if(queues.peek().getTimeStamp() < timestamp) {
                    Value value = queues.poll();
                    list.add(value);
                    results = value.getValue();
                }
                size--;
            }

            queues.addAll(list);
            return results == null ? "": results;
        }
    }
}

class Value{

    private String value;
    private int timeStamp;

    public Value(String value, int timeStamp) {
        this.value = value;
        this.timeStamp = timeStamp;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}