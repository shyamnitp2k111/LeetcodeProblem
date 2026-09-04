package javacode;

import java.util.concurrent.CompletableFuture;

/*

# 8. Chain Three Dependent APIs

### Problem

Each API depends on the previous one's output.

```

Customer API
      ↓
Order API
      ↓
Payment API

```

** What the interviewer checks **

  * Multiple dependent asynchronous calls.
  * Proper sequencing.

---

*/
public class CompletableFeature_7 {

    static void main() {
        Add add = new Add();

        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> add.add(1));  // 1 + 2 = 3
        CompletableFuture<Integer> completableFutureResult = completableFuture
                .thenCompose(x -> CompletableFuture.supplyAsync(() -> add.add(x))) // 3 + 2 = 5
                .thenCompose(x -> CompletableFuture.supplyAsync(() -> add.add(x))); // 5 + 2 = 7

        System.out.println(completableFutureResult.join()); // o/p - 7
    }
}

class Add {
    public int add(int x){
        System.out.println("value " + x);
        System.out.println("end ");
        return x + 2;

    }
}
