package companyinterview;

import java.util.*;

public class View {
    String str = "aabccc";
    //answer = b

    public static void main(String[] args) {
        String str = "aabbcccd";

        // str = "aabbcccda";


        Set<Character> set = new HashSet<>();

        Queue<Character> queue = new ArrayDeque<>();
        System.out.println(Arrays.stream(str.chars().filter(character -> set.add((char)character) ?  true : set.remove((char)character)  ).toArray()).findFirst());




       /* char ch = (char) str.chars().filter(character -> str.indexOf(character) == str.lastIndexOf(character)).findAny().getAsInt();
        System.out.println(ch);*/

        int i = 5;
    }

    public int firstUniqChar(String s) {
        Map<Character, int[]> map = new LinkedHashMap<>();

        for(int index = 0 ; index < s.length() ; index++) {

            if(map.containsKey(s.charAt(index))) {

                int[] value = map.get(s.charAt(index));
                map.put(s.charAt(index), new int[]{value[0], value[1]+1});
            } else {
                map.put(s.charAt(index), new int[]{index, 1});
            }
        }

        for(Map.Entry<Character, int[]> entry : map.entrySet()) {
            if(entry.getValue()[1] == 1) {
                return entry.getValue()[0];
            }
        }

        return -1;
    }
}
