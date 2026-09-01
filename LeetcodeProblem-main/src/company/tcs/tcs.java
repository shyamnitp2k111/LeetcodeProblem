package company.tcs;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class tcs {

    // array -> 1 to 9
    // replicated number
    // missing number
    //

    public static void main(String[] args) {

        List list = new ArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(7);
        list.add(8);
        list.add(9);
        list.add(9);
        list.add(9);


        //list.stream().distinct().
        //list.stream().collect(Collectors.groupingBy(i -> i, Collectors.counting()).

        List<Integer> list1 = IntStream.rangeClosed(1,9).filter( i -> !list.contains(i)).mapToObj(i -> i).collect(Collectors.toList());

        list1.forEach(System.out::println);

    }

}
