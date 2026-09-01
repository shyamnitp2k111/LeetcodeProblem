package javacode;

import company.wipro.ThreeAPICall;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/*
# 2. (API1 + API2) × API3

### Problem

Three APIs execute independently. First add the results of API1 and API2,
then multiply the sum by the result of API3.
*/
public class CompletableFeature_2 {
    static public void main(String[] args) {


        //first approach
        ThirdPartyAPI threeAPICall = new ThirdPartyAPI();

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(threeAPICall::m1);
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(threeAPICall::m2);
        CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(threeAPICall::m3);

        CompletableFuture<Integer> integerCompletableFuture = completableFuture1
                .thenCombine(completableFuture2, (x ,y) -> x + y)
                .thenCombine(completableFuture3, (x, y ) -> x * y);

        System.out.println("x + y * z value is .... " + integerCompletableFuture.join());

        //second approach

        List<CompletableFuture<Integer>> completableFutureList = List.of(completableFuture1, completableFuture2, completableFuture3);

        //approach -1 : add all completableFuture for joining
        CompletableFuture<Void> completableFutureAll = CompletableFuture.allOf(completableFuture1, completableFuture2, completableFuture3);

        //approach -2 : add all completableFuture for joining
        completableFutureAll = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]));

        completableFutureAll.join();

        int result = (completableFuture1.join() + completableFuture2.join()) * completableFuture3.join();

        System.out.println("second approach result ... " + result);
    }
}
