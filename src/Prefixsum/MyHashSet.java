package Prefixsum;

import java.util.ArrayList;
import java.util.List;

class MyHashSet {

    private List<Integer> list;

    public MyHashSet() {
        list = new ArrayList<>();
    }

    public void add(int key) {
        boolean flag = list.stream().anyMatch(i -> i == key);

        if(!flag) {
            list.add(key);
        }
    }

    public void remove(int key) {
        boolean flag = list.stream().anyMatch(i -> i == key);
        if(flag) {
            list.remove(Integer.valueOf(key));
        }
    }

    public boolean contains(int key) {
        return list.stream().anyMatch(i -> i == key);
    }
}