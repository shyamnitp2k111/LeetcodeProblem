package company.wipro;

import java.util.concurrent.CopyOnWriteArrayList;

public class FailFast {

    static void main() {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        // modification while iterating - output - [1, 2, 3, 4] it is not printing 5
        for(Integer i : list){
            if(i == 3) {
                list.add(5);
            }

            System.out.println(i);
        }


        // iterating - output - [1, 2, 3, 4, 5] now it is printing 5
        for(Integer i : list){
            System.out.println(i);
        }
    }
}
