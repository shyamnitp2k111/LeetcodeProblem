package company.wipro;

import java.util.stream.IntStream;

public class ReverseStringExceptSpecialCharacter {

    public static void main(String[] args) {

        String str = "a$bc";

        char[] reverseChars = str.chars()
                .filter(Character::isLetterOrDigit)
                .mapToObj(c -> (char) c)
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append)
                .reverse()
                .toString()
                .toCharArray();


        int[] index = {0};

        String result = IntStream.range(0, str.length())
                .mapToObj(i -> {

                    char ch = str.charAt(i);

                    if (Character.isLetterOrDigit(ch)) {
                        return reverseChars[index[0]++];
                    }

                    return ch;
                })
                .map(String::valueOf)
                .collect(StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append)
                .toString();


        System.out.println(result);
    }
}