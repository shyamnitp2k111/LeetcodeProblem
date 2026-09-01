package company.wipro;

import java.util.Arrays;
import java.util.List;

public class InterviewQuestion {

    public static void main(String[] args) {

        // input - [["ABC", "Shyam", "" ]]
        // output - [[ CBA , mayhS, jareehD ]]


        List<String> listOfName = Arrays.asList("ABC", "Shyam", "Dheeraj");


        List<StringBuffer> result = listOfName.stream().
                map(str -> new StringBuffer(str).reverse()).
                toList();

        result.forEach(System.out::println);

    }
}
