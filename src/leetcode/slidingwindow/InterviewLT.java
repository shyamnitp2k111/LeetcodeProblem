package leetcode.slidingwindow;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InterviewLT {

    public static void main(String[] args) {
        //array [1,2,....,50]
        // prime number

        // sub -1 each number from array

        List<Integer> list = new ArrayList<>();

        int[] a = IntStream.range(1,50).toArray();

        Arrays.stream(a).filter(i-> {
                    for(int index = 2 ; index < i ; index++) {
                        if(i % index == 0 ) {
                            return false;
                        }
                    }
                    return true;}
            ).map(i -> i -1).forEach(System.out::println);



        list.stream().collect(Collectors.toSet());
        int[] b = IntStream.rangeClosed(1,50).toArray();



    }
}
