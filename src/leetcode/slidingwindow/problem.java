package leetcode.slidingwindow;

import java.util.ArrayList;
import java.util.List;

public class problem {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();

        list.add(0);
        list.add(0);
        list.add(0);
        logics(list, 12);
    }

    static int logics(List<Integer> list, int key) {
        return search(list, 0,list.size(),key);
    }

    static int search(List<Integer> list, int startIndex, int endIndex, int key) {

        // O(logn) - time complexity
        // O(n) - space complexity
        //[10, 15, 1, 3, 8] key 15
        int med = (startIndex + endIndex)/2;
        if(list.get(med) == key) {
            return med;
        }


        if(list.get(med) <= list.get(endIndex)) {

            if(list.get(med) < key && list.get(endIndex) >= key) {
                return search(list, med+1, endIndex, key);
            } else {
                return search(list, 0, med -1, key);
            }

        } else {
            if(list.get(med) > key && list.get(startIndex) <= key) {
                return search(list, 0, med -1, key);
            } else {
                return search(list, med+1, endIndex, key);
            }
        }
    }
}
