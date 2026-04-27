package leetcode.slidingwindow;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Products {
    public static void main(String[] args) {

        Products products = new Products();
        int[] results = products.maxSlidingWindow(new int[]{1,2,1,0,4,2,6}, 3);
        Arrays.stream(results).forEach(System.out::println);

       // System.out.println(products.isPalindrome("shyaayshs"));
    }


    public int[] maxSlidingWindow(int[] nums, int k) {


        int [] results = new int[nums.length - k + 1];
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b) -> b-a);


        for(int index = 0 ; index < 3 ; index++) {
            queue.offer(nums[index]);
        }

        int i = 0;
        results[i] = queue.peek();

        for(int index = k ; index < nums.length ; index++) {
            queue.remove(nums[index -k]);
            queue.add(nums[index]);
            results[++i] = queue.peek();
        }
        return results;
    }
}
