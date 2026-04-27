package TestBasic;

public class test {

    public static void main(String[] args) {
        A test1 = new A();
        test1.i =100;

        System.out.println(test1.i);
        A test2 = new A();
        test2.i =200;

        System.out.println(test2.i);
        System.out.println(test1.i);
    }
}

class A {
    static int i =10;
}
