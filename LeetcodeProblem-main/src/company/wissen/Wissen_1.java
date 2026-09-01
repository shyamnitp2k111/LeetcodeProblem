package company.wissen;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Wissen_1 {

    static public void main(String[] args) {
        // Question 1
        //str ---> find first non - repeated character
        // str ->  "Shhayay" -> s

        String str = "ShhxayaySb";

        Map.Entry<Character, Long> result = str.chars().mapToObj(ch -> (char)ch).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream().filter(entry -> entry.getValue() == 1).findFirst().orElse(null);

        System.out.println(result.getKey());

        //Question 2
        // sort the array 0 1

        int[] array = {0, 1,0, 1, 0,1,1};


        int left = 0;
        int right = array.length -1;

        while(left < right) {
            if(array[left] == 1 && array[right] == 0) {
                int temp = array[left];
                array[left] = array[right];
                array[right] = temp;
                left++;
                right--;
            } else if(array[left] == 0 && array[right] == 1) {
                left++;
                right--;
            } else if(array[left] == 0 && array[right] == 0) {
                left++;
            } else if(array[left] == 1 && array[right] == 1) {
                right--;
            }
        }

        for(int index = 0 ; index < array.length; index++) {
            System.out.println(array[index]);
        }
    }
}
