package IRFramework;

import java.time.Instant;
import java.util.Random;

public class Test {
    public static int square(int x) {

        return x * x;
    }

    public static void main(String[] args) {

        Instant instant = Instant.now();
        System.out.println("Instant time: " + instant);

        for(int i =0 ; i < 10 ; i++) {
            System.out.println(Instant.now().toString());
        }


        System.out.println(System.currentTimeMillis());

        Random random2 = new Random(42);
        System.out.println(random2.nextInt());
        System.out.println(random2.nextInt());
        for (int i = 0; i < 1_000_000; i++) {
            square(i);
        }
        System.out.println("Finished ....");
    }
}
