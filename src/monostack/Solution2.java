package monostack;

import java.util.*;

class Solution2 {

    public static void main(String[] args) {
        Solution2 solution = new Solution2();
        System.out.println(solution.removeDuplicateLetters("cbacdcbc"));
    }

    public String removeDuplicateLetters(String s) {

        Stack<Character> stack = new Stack<>();
        Map<Character, Integer> map = new HashMap<>();
        Set<Character> set = new HashSet<>();
        StringBuilder stringBuilder = new StringBuilder();

        int length = s.length();

        for(int index = 0 ; index< length; index++) {
            map.put(s.charAt(index), map.getOrDefault(s.charAt(index), 0) + 1);
        }

        for(int index = 0 ; index < length; index++) {

            char ch = s.charAt(index);

            map.put(ch, map.get(ch) - 1);
            if (set.contains(ch)) {
                continue;
            }

            while(!stack.isEmpty() && stack.peek() > ch && map.get(stack.peek()) > 0) {
                set.remove(stack.pop());
            }

            set.add(ch);
            stack.push(ch);
        }

        while (!stack.isEmpty()) {
            stringBuilder.append(stack.pop());
        }

        return stringBuilder.reverse().toString();
    }
}