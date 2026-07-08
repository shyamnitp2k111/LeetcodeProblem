package company.wipro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class BalanceParathesis {

    public static void main(String[] args) {

        String input = "{[]}";

        System.out.println(isBalancedUsingSet(input));
        System.out.println(isBalancedUsingHashMap(input));
    }


    public static boolean isBalancedUsingSet(String input) {

        Set<Character> openingBrackets = Set.of('{', '[', '(');

        Stack<Character> stack = new Stack<>();

        boolean flag = true;

        for (int index = 0; index < input.length(); index++) {

            char current = input.charAt(index);

            // Opening bracket
            if (openingBrackets.contains(current)) {

                stack.push(current);

            } else {

                // Closing bracket without opening bracket
                if (stack.isEmpty()) {
                    flag = false;
                    break;
                }

                char top = stack.pop();

                if (current == ']' && top != '[') {
                    flag = false;
                    break;
                }

                if (current == '}' && top != '{') {
                    flag = false;
                    break;
                }

                if (current == ')' && top != '(') {
                    flag = false;
                    break;
                }
            }
        }

        // Remaining opening brackets means invalid
        return flag && stack.isEmpty();
    }

    public static boolean isBalancedUsingHashMap(String input) {

        Map<Character, Character> bracketMap = new HashMap<>();

        bracketMap.put(']', '[');
        bracketMap.put('}', '{');
        bracketMap.put(')', '(');


        Stack<Character> stack = new Stack<>();


        for (int index = 0; index < input.length(); index++) {

            char current = input.charAt(index);


            // Closing bracket
            if (bracketMap.containsKey(current)) {

                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                if (top != bracketMap.get(current)) {
                    return false;
                }

            } else {

                // Opening bracket
                stack.push(current);
            }
        }


        return stack.isEmpty();
    }

}