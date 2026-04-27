#include <jni.h>
#include "./jni_JNIDemo.h"
#include <iostream>


//message from native
JNIEXPORT void JNICALL Java_jni_JNIDemo_printHello
  (JNIEnv * env, jobject jobj) {
  printf("Hello from Native\n");
}


//example of primitive type
JNIEXPORT jint JNICALL Java_jni_JNIDemo_sum
  (JNIEnv *env, jobject jobj, jint firstNumber, jint secondNumber) {

   jint results = firstNumber + secondNumber ;
   return results;
}


//sum of array
JNIEXPORT jint JNICALL Java_jni_JNIDemo_calArraySum(JNIEnv *env, jobject obj, jintArray inJNIArray) {

     // Step 1: Convert the incoming JNI jintarray to C's jint[]
     jboolean iscopy = JNI_FALSE;
     jint *inCArray = env->GetIntArrayElements(inJNIArray, &iscopy);

     jsize length = env->GetArrayLength(inJNIArray);

     // Step 2: Perform  operations
     jint sum = 0;
     int i;
     for (i = 0; i < length; i++) {
        sum += inCArray[i];
     }
     return sum;
}



//modify instant variable
JNIEXPORT void JNICALL Java_jni_JNIDemo_modifyInstanceVariable(JNIEnv *env , jobject obj) {
   // Get the class of the object
   jclass cls = env->GetObjectClass(obj);

   // Get the field ID of the 'instanceVariable' instance variable
   jfieldID fid = env->GetFieldID(cls, "instanceVariable", "I");

   // Modify the value of instanceVariable (setting it to 1 for example)
   env->SetIntField(obj, fid, 1);
}



//modify static variable
JNIEXPORT void JNICALL Java_jni_JNIDemo_modifyStaticVariable
  (JNIEnv *env , jclass cls) {

   // Get the field ID of the static variable 'staticVariable'
   jfieldID fid = env->GetStaticFieldID(cls, "staticVariable", "I");

   // Modify the value of staticVariable (set it to 1 for example)
   env->SetStaticIntField(cls, fid, 1);

}


//call static and instance method
JNIEXPORT void JNICALL Java_jni_JNIDemo_invokeMethodFromJNI
  (JNIEnv *env, jobject obj) {

    // Calling the static method
    jclass clazz = env->FindClass("./JNIDemo");

    if (clazz == nullptr) {
        printf("class not found ...\n");
    }
    // Get the method ID for the static method
    jmethodID staticMethodID = env->GetStaticMethodID(clazz, "staticMethod", "()V");
    if (staticMethodID == nullptr) {
        printf("static method id not found ...\n");
    }

    // Call the static method
    env->CallStaticVoidMethod(clazz, staticMethodID);
    printf("Static method called from native code\n");



    // Calling the instance method

    // Ensure the passed object is valid
    if (obj == nullptr) {
        printf("object not found ...\n");
    }

    // Get the method ID for the instance method
    jmethodID instanceMethodID = env->GetMethodID(clazz, "instanceMethod", "()V");
    if (instanceMethodID == nullptr) {
        printf("instance Method ID not found ...\n");
    }

    // Call the instance method using the 'obj' (instance) object
    env->CallVoidMethod(obj, instanceMethodID);
    printf("Instance method called from native code\n");
}