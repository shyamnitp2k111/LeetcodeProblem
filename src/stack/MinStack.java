package stack;

import java.util.Stack;

public class MinStack {

    Stack<Integer> stack = new Stack<>();

    Stack<Integer> minStack = new Stack<>();


    //3,5,1,4,2,

    //3,3,1,1,1,

    public int getMin() {
        return minStack.peek();
    }

    public void pushs(int value ) {
        stack.add(value);

        if(minStack.peek() < value) {
            minStack.add(minStack.peek());
        } else {
            minStack.add(value);
        }
    }

    public int pops() {

        if(!stack.isEmpty()) {
            stack.pop();
            return minStack.pop();
        } else {
            return -1;
        }
    }

    public Stack<Integer> getStack() {
        return stack;
    }

    public Stack<Integer> getMinStack() {
        return minStack;
    }
}
