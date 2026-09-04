package javacode;

/*
# 7. Call Dependent API

### Problem
The second API requires the output of the first API.

Example:

```
UserId API
      ↓
User Details API
```

**What the interviewer checks**

* Dependent asynchronous execution.
* Avoiding nested futures.


A simple way to remember is:

thenApply() → Transform a value.
thenCombine() → Combine two independent futures.
thenCompose() → Chain two dependent futures.


*/

import java.util.concurrent.CompletableFuture;

public class CompletableFeature_5 {

    static void main() {

        APIClass2 thirdPartyAPI = new APIClass2();
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(thirdPartyAPI::m1);


        // thenCompose() → Chain two dependent futures.
        CompletableFuture<Integer> dependencyResult  = completableFuture1
                .thenCompose(x -> CompletableFuture.supplyAsync(() -> thirdPartyAPI.m2(x)));

        System.out.println(dependencyResult.join());

    }
}

class APIClass2 {
    public int m1() {
        return 10 * 10;
    }

    public int m2(int val) {
        System.out.println("value is val " + val);
        return val * 2;
    }
}
