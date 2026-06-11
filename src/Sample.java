import java.util.LinkedList;
import java.util.Queue;

public class Sample {

    public static void main(String[] args) {
        Sample sample = new Sample();
        System.out.println(sample.findTheWinner(5,2));
    }

    public int findTheWinner(int n, int k) {
        Queue<Integer> queue = new LinkedList<>();

        for(int index = 1 ; index <= n; index++) {
            queue.offer(index);
        }

        int val;
        while (queue.size() > 1) {
            val = k;
            while (val > 1 ) {
                queue.offer(queue.poll());
                val--;
            }


            System.out.println("removed element "+ queue.poll());
        }
        return queue.isEmpty() ? 0 : queue.poll();
    }
}
