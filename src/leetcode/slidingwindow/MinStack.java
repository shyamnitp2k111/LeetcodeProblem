package leetcode.slidingwindow;

import java.util.Stack;

class MinStack {

    Stack<Integer> stack ;
    Stack<Integer> minStack;

    public MinStack() {

        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int val) {
        stack.push(val);
        int min;
        if(minStack.isEmpty()) {
            min = val;
        } else {
            min = Math.min(minStack.peek(), val);
        }
        minStack.push(min);
    }
    
    public void pop() {
        stack.pop();
        minStack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}