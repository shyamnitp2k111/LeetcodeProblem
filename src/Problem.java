import java.util.List;

public class Problem {

    //JP Morgan


    private List<Integer> stockPrice;
    int minStockPrice;
    private int maxProfit;
    int length;


    public Problem(List<Integer> stockPrice) {
        this.stockPrice = stockPrice;
        minStockPrice = Integer.MAX_VALUE;
        maxProfit = Integer.MIN_VALUE;
    }

    public static void main(String[] args) {
        //int[] stockPrice = {9,1,5,8,7};
        int[] stockPrice = {7,5,4,3};
        int buy = stockPrice[0];
        int maxProfit = Integer.MIN_VALUE;
        int sell;

        for(int index = 0 ; index < stockPrice.length -1; index++) {
            for(int in = index + 1; in < stockPrice.length; in++) {
                maxProfit = Math.max(maxProfit, stockPrice[in] - stockPrice[index]);
            }
        }

        maxProfit = maxProfit == -1 ? 0 : maxProfit;
        System.out.println(maxProfit);


        ///
    }

    //calculate method

    public int bestProfitFromGivenData() {
        int maxProfit = Integer.MIN_VALUE;

        for(int index = 0 ; index < stockPrice.size() -1; index++) {
            for(int in = index + 1; in < stockPrice.size(); in++) {
                maxProfit = Math.max(maxProfit, stockPrice.get(in) - stockPrice.get(index));
            }
        }

        //{9,1,5,8,7}

        // 0,-8, 4, 7, 6

        //min st

        maxProfit = maxProfit == -1 ? 0 : maxProfit;
        System.out.println(maxProfit);
        return maxProfit;
    }

    public void add(int val) {
        //add value
        stockPrice.add(val);
    }

}
