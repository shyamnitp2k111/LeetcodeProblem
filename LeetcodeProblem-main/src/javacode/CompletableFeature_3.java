package javacode;

/*# 4. Maximum of 3 APIs

### Problem

Three APIs return numbers. Find the largest value among them.*/


import java.util.List;
import java.util.concurrent.CompletableFuture;

public class CompletableFeature_3 {
    static void main() {
        ThirdPartyAPI thirdPartyAPI = new ThirdPartyAPI();


        //approach -1
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(thirdPartyAPI::m1);
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(thirdPartyAPI::m2);
        CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(thirdPartyAPI::m3);

        CompletableFuture<Integer> completableFuture = completableFuture1.thenCombine(completableFuture2, (x,y) -> Math.max(x,y))
                .thenCombine(completableFuture3, (x,y) -> Math.max(x, y));

        System.out.println("max value among three api response is ... "+ completableFuture.join());


        //approach - 2
        List<CompletableFuture<Integer>> completableFutureList = List.of(completableFuture1, completableFuture2, completableFuture3);
        CompletableFuture<Void> completableFutureAll = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));

        completableFutureAll.join();

        int result = Math.max(Math.max(completableFuture1.join(), completableFuture2.join()), completableFuture3.join());

        System.out.println("[Approach 2 ] max value among three api response is "+result);


    }
}
