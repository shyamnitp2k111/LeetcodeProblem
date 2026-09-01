package jni.demo;

public class Demo {

    // Load native library
    static {
        System.loadLibrary("native");
    }

    // Declare native method
    public native void printMessage();

    public static void main(String[] args) {
        // Call native method
        new Demo().printMessage();
    }
}
