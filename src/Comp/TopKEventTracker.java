package Comp;

import java.util.*;
import java.lang.*;

/**
 * TopKEventTracker maintains the top K most frequent event names
 * from a continuously incoming stream of events.
 *
 * Ranking Rules:
 * 1. Higher frequency comes first.
 * 2. If frequencies are equal, lexicographically smaller event name comes first.
 */
class TopKEventTracker {

    /**
     * Constructs a TopKEventTracker with the given value of K.
     *
     * @param k the maximum number of top frequent events to return
     */

    private Map<String, Integer> map =  new HashMap() ;
    private Queue<String> queue = new PriorityQueue((a,b) -> {

        int freq = Integer.compare(map.get(a) , map.get(b));
        if(freq == 0) {

            return 1 ;
            //return a.toString() - b.toString() ;
        } else {
            return freq;
        }
    } );

    private int k;

    public TopKEventTracker(int k) {
        this.k = k;
    }

    /**
     * Records one occurrence of the given event name.
     * If the event does not exist, it is added with frequency 1.
     * If it already exists, its frequency is incremented.
     *
     * @param eventName the name of the event to record
     */
    public void record(String eventName) {
        map.put(eventName, map.getOrDefault(eventName, 0) +1);
        queue.add(eventName);

        if(queue.size() > k) {
            queue.poll();
        }
    }

    /**
     * Returns a list of the current top K event names ranked by:
     * 1. Higher frequency first
     * 2. Lexicographically smaller name if frequencies are equal
     *
     * If fewer than K unique events exist, all events are returned.
     *
     * @return a list of event names representing the top K events
     */
    public List<String> topK() {
        List<String> topKEvent = new ArrayList();

        Queue<String> queueCopy = new PriorityQueue(queue);

        for(int index = 1 ; index <= k ; index++) {
            topKEvent.add((String)queueCopy.poll());
        }
        return topKEvent;
    }
}

/**
 * Your TopKEventTracker object will be instantiated and called as such:
 *
 * TopKEventTracker obj = new TopKEventTracker(k);
 * obj.record(eventName);
 * List<String> param_2 = obj.topK();
 */