package javacode;

import java.util.concurrent.CompletableFuture;

/*

# 6. Square Every API Result Then Add

### Problem

Each API returns a number. Square each number individually and then calculate the total.

**What the interviewer checks**

* Transforming future results.
* Applying operations before combining.

---

*/
public class CompletableFeature_6 {
    static void main() {
        ThirdPartyAPI thirdPartyAPI = new ThirdPartyAPI();

        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(thirdPartyAPI::m1);
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(thirdPartyAPI::m2);
        CompletableFuture<Integer> completableFuture3 = CompletableFuture.supplyAsync(thirdPartyAPI::m3);

        CompletableFuture<Integer> completableFuture4 = completableFuture1.thenApply(x -> x * x);
        CompletableFuture<Integer> completableFuture5 = completableFuture2.thenApply(x -> x * x);
        CompletableFuture<Integer> completableFuture6 = completableFuture3.thenApply(x -> x * x);

        System.out.println(completableFuture4.join() + completableFuture5.join() + completableFuture6.join());

    }
}
