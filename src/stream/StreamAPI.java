package stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

public class StreamAPI {
    public static void main(String[] args) {


        //Given a list of integers, filter the even numbers.

        System.out.println("----------------------Given a list of integers, filter the even numbers.-------------------------------------");
        int[] array = IntStream.rangeClosed(1,50).toArray();
        Arrays.stream(array).filter(number -> number % 2 == 0 ).forEach(System.out::println);
        System.out.println("---------------------");


        //Find Maximum in a List
        System.out.println("-------Find Maximum in a List--------------");
        array = IntStream.rangeClosed(1,50).toArray();
        System.out.println(Arrays.stream(array).max().getAsInt());
        System.out.println("---------------------");

        //Sort a List
        System.out.println("---------Sort a List------------");
        int[] arr = new int[]{2,1,4,7,9,5};
        Arrays.stream(arr).sorted().forEach(System.out::println);
        System.out.println("---------------------");


        System.out.println("---------Sort a List------------");
        List<Integer> list = Arrays.asList(1,2,3,1,2);
        list.stream().sorted(Comparator.reverseOrder()).forEach(System.out::println);
        System.out.println("---------------------");



        System.out.println("--------Count strings starting with a specific prefix, e.g., “A”.-------------");
        List<String> lists = Arrays.asList("Shyam", "Alice", "Amex");
        lists.stream().filter( name -> name.charAt(0) == 'A').forEach(System.out::println);
        System.out.println("---------------------");


        System.out.println("--------Find First Non-Repeated Character in a String--------");
        String names = "Shyamaary";
        System.out.println(names.chars().mapToObj(c -> (char)c).filter(chararcter -> names.indexOf(chararcter) == names.lastIndexOf(chararcter)).findFirst().toString());
        System.out.println("---------------------");


        System.out.println("--------Convert all strings in a list to uppercase. -----------");
        List<String> listOfNames = Arrays.asList("Shyamaary","Ram");

        listOfNames.stream().map(String::toUpperCase).forEach(System.out::println);
        System.out.println("---------------------");


        System.out.println("----------Calculate the sum of all numbers in a list.-----------");
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(numbers.stream().mapToInt( val -> val).sum());
        System.out.println("---------------------");


        System.out.println("---------------- Check if any string in a list contains API.----------------");

        List<String> listOfStr = Arrays.asList("Shyam","Stream API");
        boolean value = listOfStr.stream().anyMatch(str -> str.contains("API"));
        System.out.println(value);

        System.out.println("---------------------");
    }
}
