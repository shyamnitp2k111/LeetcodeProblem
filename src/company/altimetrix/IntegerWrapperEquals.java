package company.altimetrix;

public class IntegerWrapperEquals {

    /*
    ✔ ==  → Compares object references
    ✔ equals() → Compares values

Integer Cache Range:
-128 to 127

100 → Cached

    Integer a = 100;
    Integer b = 100;
    a == b   // true

200 → Not Cached

    Integer a = 200;
    Integer b = 200;
    a == b   // false

    Reason:


    Autoboxing uses Integer.valueOf()
    valueOf() returns:
            - Cached object for -128 to 127
            - New object outside this range

    Remember:
            ==     → Reference comparison
    equals → Value comparison

    Interview Rule:

    Never use == to compare wrapper classes (Integer, Long, Double, etc.).
    Use equals() (or Objects.equals() if null is possible).

*/

    public static void main(String[] args) {
        IntegerWrapperEquals integerWrapperExampleEqual = new IntegerWrapperEquals();


        //without equals
        System.out.println(integerWrapperExampleEqual.compareValue(100,100));
        System.out.println(integerWrapperExampleEqual.compareValue(200,200));


        //with equals
        System.out.println(integerWrapperExampleEqual.compareValueWithEquals(100,100));
        System.out.println(integerWrapperExampleEqual.compareValueWithEquals(200,200));
    }

    public boolean compareValue(Integer val1, Integer val2) {
        if(val1 == val2) {
            return true;
        } else {
            return false;
        }
    }

    public boolean compareValueWithEquals(Integer val1, Integer val2) {
        return val1.equals(val2);
    }
}
