package company.wipro;

import java.util.concurrent.CompletableFuture;

public class ThreeAPICall {

    static public void main(String[] args) {

        ThirdPartyAPI thirdPartyAPI = new ThirdPartyAPI();
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync( () -> {
            try {
                return thirdPartyAPI.m1();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync( () -> {
            try {
                return thirdPartyAPI.m2();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Integer> f3 = CompletableFuture.supplyAsync( () -> {
            try {
                return thirdPartyAPI.m3();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<Integer> completableFuture = f1.thenCombine(f2, (x, y) -> x+y)
                .thenCombine(f3, (x , y ) -> x+y);

        System.out.println(completableFuture.join());
    }

}

class ThirdPartyAPI {

    public int m1() throws InterruptedException {
        Thread.sleep(1000);
        return 10;
    }

    public int m2() throws InterruptedException {
        Thread.sleep(1000);
        return 20;
    }

    public int m3() throws InterruptedException {
        Thread.sleep(1000);
        return 30;
    }
}
