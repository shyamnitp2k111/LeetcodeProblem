package multithread.completeablefeauture;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class CustomThread {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("shyam");
            int val = 10/0;
            return 10;
        }).exceptionally( e -> {
            return 0;
        });

        System.out.println(completableFuture.get());

        CompletableFuture<Void> completableFuture1 = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> System.out.println("task - 1")).thenApplyAsync((v) -> {
                    System.out.println("task -2 ");
                    return "c";
                }));

        System.out.println(completableFuture1.get());

        System.out.println("main thread ...");

        List<String> list = List.of("a1", "a2");


        List<CompletableFuture<Integer>> completableFutureList = list.stream().map( api ->
                    CompletableFuture.supplyAsync(() ->  10).exceptionally(e -> 0).completeOnTimeout(0, 2, TimeUnit.SECONDS)).toList();

        CompletableFuture.allOf(completableFutureList.toArray(CompletableFuture[]::new)).join();

        double average = completableFutureList.stream().mapToInt(CompletableFuture::join).average().orElse(0);

        System.out.println(average);

    }
}
