package companyinterview;

import java.util.regex.*;
import java.util.*;

public class SumAmounts {
    public static void main(String[] args) {
        String input = "* renter jghjgj $1200 kdlkjhgf $100";

        Pattern pattern = Pattern.compile("\\$(\\d+)");
        Matcher matcher = pattern.matcher(input);

        int sum = 0;

        while (matcher.find()) {
            int amount = Integer.parseInt(matcher.group(1));
            sum += amount;
        }

        System.out.println("Total sum: " + sum);
    }
}