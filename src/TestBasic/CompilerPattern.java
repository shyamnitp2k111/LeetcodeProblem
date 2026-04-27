package TestBasic;

import java.util.regex.Pattern;

public class CompilerPattern {

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(".*stress.process.properties file has created*");

        if(pattern.matcher("stress.process.properties file has created").matches()) {
            System.out.println("Match found ......");
        }
    }
}
