#include <jni.h>
#include "./jni_example_CriticalRegionExample.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_jni_example_CriticalRegionExample_modifyArray
      (JNIEnv *env , jobject obj, jintArray arr) {

    // Step 1: Get a critical reference to the array
    jint *arrayElements = (jint*) env->GetPrimitiveArrayCritical(arr, NULL);

    if (arrayElements == NULL) {
        // Handle error if the array couldn't be accessed
        return;
    }

    // Step 2: Modify the array elements
    jsize length = env->GetArrayLength(arr);
    for (jsize i = 0; i < length; i++) {
        // Double each element
        arrayElements[i] *= 2;
    }

    // Step 3: Release the critical reference and commit changes to Java array
    env->ReleasePrimitiveArrayCritical(arr, arrayElements, JNI_COMMIT);

}