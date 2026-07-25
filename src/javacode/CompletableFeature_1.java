package javacode;


/*# 1. Sum of 3 APIs

### Problem

You have three independent APIs running in parallel. Each returns an integer. After all three complete,
calculate their total sum.


thenApply() → Transform a value.
thenCombine() → Combine two independent futures.
thenCompose() → Chain two dependent futures.

*/

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class CompletableFeature_1 {
    static void main() {

        //first approach
        ThirdPartyAPI threeAPICall = new ThirdPartyAPI();
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(threeAPICall::m1);
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(threeAPICall::m2);
        CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(threeAPICall::m3);

        CompletableFuture<Integer> result = completableFuture1.thenCombine(completableFuture2, (x,y) -> x+y)
                .thenCombine(completableFuture3, (sum , z) -> sum + z);

        System.out.println(result.join());

        //second approach
        List<Supplier<Integer>> list = List.of(threeAPICall::m1, threeAPICall::m2, threeAPICall::m3);

        List<CompletableFuture<Integer>> completableFutureList = list.stream()
                .map(CompletableFuture::supplyAsync).toList();

        CompletableFuture<Void> completableFutureResult = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));

        completableFutureResult.join();

        int res = completableFutureList.stream().mapToInt(i -> i.join()).sum();

        System.out.println("second result " + res);
    }
}


class ThirdPartyAPI {

    public int m1()  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 10;
    }

    public int m2() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 20;
    }

    public int m3() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 30;
    }
}
