package javacode;

import java.util.concurrent.CompletableFuture;

public class CompletableFeature_4 {

    static void main() {

        APIClass apiClass = new APIClass();
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> apiClass.m1(10));
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            apiClass.m2(10);
            return 2;
        });

        CompletableFuture<Integer> completableFuture = completableFuture1.thenCombine(completableFuture2, (x, y) -> x + y);
        System.out.println(completableFuture.join());
    }
}

class APIClass {
    public int m1(int val) {
        return val * val;
    }

    public void m2(int val) {
        System.out.println("value is val " + val);
    }
}
