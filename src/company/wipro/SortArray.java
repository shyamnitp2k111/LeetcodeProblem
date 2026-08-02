package company.wipro;

import java.util.Arrays;

public class SortArray {
    static void main() {
        int[] arr = {1, 4 , 2, 3};


        for(int i = 0; i < arr.length; i++) {
            for(int j = 0 ; j < i; j++) {
                if(arr[i] < arr[j]) {
                    int a = arr[i];
                    arr[i] = arr[j];
                    arr[j] = a;
                }
            }
        }


        Arrays.stream(arr).forEach(System.out::println);
    }
}
