package code;

import java.util.*;
import java.util.stream.Collectors;

public class Paytm1 {

    public static void main(String[] args) {
        //500, 404, 500, 401, 404, 500, 403, 404, 404

        int[] a = {500, 404, 500, 401, 404, 500, 403, 404, 404};

        Map<Integer, Integer> map = new HashMap<>();

        for(int index = 0 ; index < a.length; index++) {
            map.put(a[index], map.getOrDefault(a[index], 0 )+ 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((a1, b1)-> b1.getValue()- a1.getValue() );

        for(Map.Entry entry : map.entrySet()) {
            queue.offer(entry);
        }

        System.out.println(queue.poll());
        System.out.println(queue.poll());



        /// list of transaction -> category and amount

        List<Transaction> list = new ArrayList<>();


        list.stream().collect(Collectors.groupingBy(category -> category, Collectors.counting()));


    }

}

class Transaction {

    private String category;
    private int amount;

    public Transaction(String category, int amount) {
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "category='" + category + '\'' +
                ", amount=" + amount +
                '}';
    }
}




