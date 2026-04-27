package stack;

import java.util.Stack;

class StockSpanner {

    Stack<int[]> stack ;
    public StockSpanner() {
        stack = new Stack<>();
    }

    public int next(int price) {

        if(stack.isEmpty()) {
            stack.push(new int[]{price, 1});
            return 1;
        } else {
            int count = 0;
            while (!stack.isEmpty() && stack.peek()[0] < price){
                int[] val = stack.pop();
                count = count + val[1];
            }

            if(stack.isEmpty()) {
                stack.push(new int[]{price,  count});
            } else {
                stack.push(new int[]{price, 1 + count});
            }
            return stack.peek()[1];
        }
    }
}