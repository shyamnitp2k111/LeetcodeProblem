package jni;

public class JNIDemo {

    // Instance variables
    private int instanceVariable;

    //static variable
    private static int staticVariable;

    // static method
    public static void staticMethod() {
        System.out.println("static method called from jni ");
    }

    // instance method
    public void instanceMethod() {
        System.out.println("instance method called from jni ");
    }

    static {
        /*Properties props = new Properties();
        props.setProperty("java.library.path","/Users/skishor/Documents/SampleProject/src/jni");
        System.setProperties(props);
        System.load("/Users/skishor/Documents/SampleProject/src/jni/libGetVersion.dylib");*/
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        //System.out.println("value is ........."+System.getProperty("java.library.path"));


        //print message from c++
        JNIDemo jniDemo = new JNIDemo();
        jniDemo.printHello();



        //primitive type example
        System.out.println("\n---------Primitive type Example  -----------");
        System.out.println("sum of 10 and 20 is ...."+jniDemo.sum(10, 20));


        //Array example
        System.out.println("\n---------Example of Array -----------");
        int[] numbers = {11, 22};
        System.out.println("sum of array element ..."+ jniDemo.calArraySum(numbers));


        //Modify instance variable
        System.out.println("\n---------Example of instance variable-----------");
        System.out.println("[Before] value of instance variable is  "+ jniDemo.instanceVariable);
        jniDemo.modifyInstanceVariable();
        System.out.println("[After] value of instance variable is  "+ jniDemo.instanceVariable);


        //Modify static variable
        System.out.println("\n---------Example of static variable-----------");
        System.out.println("[Before] value of static variable is  "+ staticVariable);
        modifyStaticVariable();
        System.out.println("[After] value of static variable is  "+ staticVariable);


        //call static and instance method from jni
        System.out.println("\n---------invoke static and instance method from jni-----------");
        jniDemo.invokeMethodFromJNI();

    }

    public native void invokeMethodFromJNI() ;

    public static native void modifyStaticVariable();

    public native void modifyInstanceVariable() ;

    public native int calArraySum(int[] numbers);

    public native void printHello() ;

    public native int sum(int firstNumber, int secondNumber);


}
