package jni.example;

import java.util.Arrays;

public class CriticalRegionExample {

    // Declare the native method
    public native void modifyArray(int[] arr);

    // Load the native library
    static {
        System.loadLibrary("JNIDemoLibrary");
    }

    public static void main(String[] args) {
        CriticalRegionExample criticalRegionExample = new CriticalRegionExample();
        int[] numbers = {1, 2, 3, 4, 5};

        System.out.println("Before JNI call:");
        Arrays.stream(numbers).forEach(System.out::println);

        System.out.println();

        // Call native method to modify the array
        criticalRegionExample.modifyArray(numbers);

        System.out.println("After JNI call:");
        Arrays.stream(numbers).forEach(System.out::println);
        System.out.println();
    }
}
