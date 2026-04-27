package companyinterview;

import java.util.LinkedHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TCSProblem {

    public static void main(String[] args) {
         String text = "ava RestAPI MicroServices SpringBoot";


         for(int index = 0 ; index < text.length(); index++) {

             char ch = text.charAt(index);
             if(text.indexOf(ch) == text.lastIndexOf(ch)) {
                 System.out.println(ch);
                 break;
             }
         }


         //java 8
         int ch = text.chars().filter(s-> text.indexOf(s)  == text.lastIndexOf(s)).findFirst().getAsInt();

        System.out.println((char) ch);


        char[] array = text.toCharArray();

        //Arrays.stream(text.chars().toArray()).collect(e -> e, Collections.)
       // text.chars().collect( Function.identity(), Collectors.counting());

        char answer = text.chars().mapToObj(c -> (char) c).
                collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new,Collectors.counting())).entrySet().stream()
                .filter(entry -> entry.getValue() == 1).map(e -> e.getKey()).findFirst().get();

        System.out.println(answer);


        System.out.println(text.chars().mapToObj(c -> (char) c).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())).
                entrySet().stream().filter( entry -> entry.getValue() == 1).map(entry -> entry.getKey()).findFirst().get());

    }
}
