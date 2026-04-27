package jni.jep423;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.List;

public class JEP423 {

    // Declare the native method
    public native void modifyArray(int[] arr);

    // Load the native library
    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) throws InterruptedException {

        //check enable gc
        List<GarbageCollectorMXBean> gcBeans = ManagementFactory.getGarbageCollectorMXBeans();

        // Iterate through the list and print the name of each garbage collector
        System.out.println("Enabled Garbage Collectors:");
        for (GarbageCollectorMXBean gcBean : gcBeans) {
            System.out.println("gc type ...." + gcBean.getName());
        }

        //call native method
        JEP423 jep423 = new JEP423();
        jep423.invokeNativeMethod(jep423);

        for(int index = 0 ; index < 1000;index++) {
            // System.out.println(index);
            new Thread(() -> {
                // call native method from the thread
                jep423.invokeNativeMethod(jep423);
            }).start();
        }

        System.out.println("--------- END -----------");
    }

    private void invokeNativeMethod(JEP423 jep423 ) {
        int[] numbers = {1, 2, 3, 4, 5};

        System.out.println("Before JNI call:");
        Arrays.stream(numbers).forEach(System.out::println);
        System.out.println();

        // Call native method to modify the array
        jep423.modifyArray(numbers);


        System.out.println("After JNI call:");
        Arrays.stream(numbers).forEach(System.out::println);
        System.out.println();

        //assign null to reference variable
        numbers = null;
    }
}
