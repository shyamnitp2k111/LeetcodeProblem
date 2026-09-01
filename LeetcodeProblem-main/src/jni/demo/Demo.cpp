#include <jni.h>
#include "./jni_demo_Demo.h"

JNIEXPORT void JNICALL Java_jni_demo_Demo_printMessage(JNIEnv *env , jobject obj) {
   printf("Message from native method");
}