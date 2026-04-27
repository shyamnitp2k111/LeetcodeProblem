package companyinterview;

public class LongestProblem {


    //Longest Substring Without Repeating Characters
    //
    //Input: "abcabcbb"
    //
    public static void main(String[] args) {


        String str = "abcdabcbb";

        int leftPointer = 0;
        int rightPointer = 0;
        int count = 1;


        while(rightPointer < str.length() ) {

            while(str.charAt(leftPointer) != str.charAt(rightPointer) && leftPointer > 0) {
                count = Math.max(count, rightPointer - leftPointer +1 );
                leftPointer --;
            }

            leftPointer = rightPointer;
            rightPointer++;
        }


        System.out.println(count);

    }
}
