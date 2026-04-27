package Comp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RingCentral {

    List<Integer> list = new ArrayList<>();
    Map<Integer, Integer> map = new HashMap<>();

    public void insert(int val) {
        if(!map.containsKey(val)) {
            map.put(val, val);
            list.add(val);
        }
    }

/*    map             list
    1,0             1
    2,1             2
    4,3             4
    5,2             5

    1, 2, 5, 4*/



    public boolean delete(int val) {
        if(map.containsKey(val)) {

            int index = map.get(val);
            int temp = list.get(list.size() -1);
            list.add(index, list.get(list.size() -1));
            list.remove(list.size() -1);
            map.put(temp, index);

            map.remove(val);

            return true;
        }
        return false;
    }

    public int getRandomValue() {

        int index =  (list.size() -1 ) * (int) Math.random();
        return 0;
    }
    public static void main(String[] args) {
        //insert
        //delete  -
        //getRandom

//insert(1)
//insert(2)
//insert(3)
//insert(4)
//insert(5)
//delete(3)
//delete(5)
//getRandomValue()



    }
}



