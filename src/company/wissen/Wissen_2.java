package company.wissen;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Wissen_2 {

    /******************************************************************************
     Find missing number:
     Given array of first (n+1) unsorted natural numbers with one missing number; find the missing number.
     Ex. {5,2,3,1} - missing number = 4

     Expected time complexity - O(n)
     Space complexity - O(1)

     n = 5
     n * (n+1)/ 2 == 15

     11

     15 - 11 =4


     *******************************************************************************/


    public static void main(String[] args) {
        int[] input = new int[]{5,2,3,1};
        //Expected answer : 4
        int missinNumber = getMissingNumber(input);
        System.out.println(missinNumber);

        String str1 = new String("Java");

        String str2 = new String("Java");

        String str3 = "Java";

        String str4 = "Java";

        System.out.println(str1 == str2);  // false
        System.out.println(str3 == str4);  // true
        System.out.println(str1 == str3);  // false


        PriorityQueue queue = new PriorityQueue();
        System.out.println("add Operation in Queue: " + queue.add("Kumar"));
        System.out.println("add Operation in Queue: " + queue.add("Hitesh"));
        System.out.println("Elements in Queue: " + queue);

        System.out.println("element Operation in Queue: " + queue.element());
        System.out.println("Elements in Queue: " + queue);

        System.out.println("peek Operation in Queue: " + queue.peek());
        System.out.println("Elements in Queue: " + queue);

        System.out.println("offer Operation in Queue: " + queue.offer("Manoj"));
        System.out.println("Elements in Queue: " + queue);

        System.out.println("remove Operation in Queue: " + queue.remove());
        System.out.println("Elements in Queue: " + queue);

        System.out.println("poll Operation in Queue: " + queue.poll());
        System.out.println("Elements in Queue: " + queue);

    }

    static int getMissingNumber(int[] input) {

        int n =  input.length + 1;
        int sumOfNutualNumber = n * ((n + 1)/2);
        int sum = Arrays.stream(input).sum();
        return sumOfNutualNumber - sum;
    }





}
