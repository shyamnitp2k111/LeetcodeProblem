package code;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Paytm {

    public static void main(String[] args) {
        //Input: arr[] = [2, 1, 1, 3, 2, 1]
        //2 → 2, 1 → 3, 3 → 1
        //Output: [1, -1, -1, 2, 1, -1]


        int[] array ={2, 1, 1, 3, 2, 1};
        Stack<int[]> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();


        for (int i : array) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        int [] results = new int[array.length];

        stack.push(new int[]{array[0], 0});

        for(int index = 1 ; index < array.length; index++) {

            while (!stack.isEmpty() && map.containsKey(stack.peek()[0]) &&
                       map.containsKey(array[index]) && map.get(stack.peek()[0]) < map.get(array[index])) {
                int[] top = stack.pop();
                results[top[1]] = array[index];
            }


            stack.push(new int[]{array[index], index});
        }

        while (!stack.isEmpty()) {
            int[] top = stack.pop();
            results[top[1]] = -1;
        }


        Arrays.stream(results).forEach(System.out::println);
    }
}
