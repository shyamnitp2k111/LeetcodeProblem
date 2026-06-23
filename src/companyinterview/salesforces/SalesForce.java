package companyinterview.salesforces;

public class SalesForce {

    public static void main(String[] args) {
        BaseClass baseClass = new BaseClass();
        System.out.println(baseClass.a);

        BaseClass baseClass1 = new ChildClass();
        System.out.println(baseClass1.a);

        ChildClass childClass = new ChildClass();
        System.out.println(childClass.a);
    }
}

class BaseClass {

    int a = m1();

    public int m1() {
        System.out.println("message from base class - value is 10");
        return 10;
    }
}

class ChildClass extends BaseClass {

    @Override
    public int m1() {
        System.out.println("message from child class - value is 11");
        return 11;
    }
}
