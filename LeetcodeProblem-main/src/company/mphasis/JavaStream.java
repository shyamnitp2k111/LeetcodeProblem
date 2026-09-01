package company.mphasis;

import java.util.List;
import java.util.OptionalDouble;

public class JavaStream {
    static void main() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6);

        OptionalDouble avergaeOfEven = list.stream().mapToInt(i -> i).filter(i -> i % 2 == 0).average();

        System.out.println(avergaeOfEven.getAsDouble());


        OptionalDouble avergaeOfOdd = list.stream().mapToInt(i -> i).filter(i -> i % 2 != 0).average();

        System.out.println(avergaeOfOdd.getAsDouble());

/*

        Given an integer array coins[ ]
        representing different denominations of currency and an integer sum.
        We need to find the number of ways we can make sum by using different combinations
        from coins[ ].

        Examples:

Input: sum = 4, coins[] = [1, 2, 3]
Output: 4
Explanation: There are four solutions: [1, 1, 1, 1], [1, 1, 2], [2, 2] and [1, 3]

Input: sum = 10, coins[] = [2, 5, 3, 6]
Output: 5
Explanation: There are five solutions:
[2, 2, 2, 2, 2], [2, 2, 3, 3], [2, 2, 6], [2, 3, 5] and [5, 5]


4 -> way(3) + 1
way(3) -> way(2) + 2


sum(1) ->
sum(2) ->
*/

        int n = 4;
        int remaining = 4;
        int noOfWays = findWay( n, new int[]{1, 2, 3}, remaining,0 );
        System.out.println(noOfWays);
    }

    private static int findWay(int n, int[] ints, int remaining, int noOfWays) {


        if(remaining == 0) {
            return  1;
        }

        if(remaining < 0) {
            return 0;
        }

        noOfWays = 0;

        for(int index = 0; index < ints.length; index++) {
            noOfWays =  noOfWays + findWay(n, ints, remaining - ints[index], noOfWays);
        }
        return noOfWays;
    }
}
