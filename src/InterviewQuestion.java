import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InterviewQuestion {

    public static void main(String[] args) {
        // Stream  ->
        //Stream -> D,E,F
        //  Steam -> X,Y, Z

        // [["ABC", "Shyam", "" ]]


        List<String> listOfName = Arrays.asList("ABC", "Shyam", "Dheeraj");


        List<StringBuffer> result = listOfName.stream().
                map(str -> new StringBuffer(str).reverse()).
                toList();

        result.forEach(System.out::println);

    }
}
