package company.altimetrix;

public class digitsum {
    public static void main(String[] args) {
       /* Sum of all digit of number using Java 8

        Int num= 124*/
        
        Integer num = 124;

        int count =  num.toString().chars().map(i -> Character.getNumericValue(i)).sum();
        System.out.println(count);

    }
}
