package stream;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class InnerOuterClassTest {

    private AtomicInteger unfoundPatternsCount = new AtomicInteger();

    public static void main(String[] args) {
        InnerOuterClassTest testObject = new InnerOuterClassTest();
        testObject.unfoundPatternsCount.set(10);
        testObject.unfoundPatternsCount.decrementAndGet();
        testObject.m();
    }


    void m(){
        new DrainerInputStreamForPatternMatching(null).start();
    }

    class DrainerInputStreamForPatternMatching extends Thread {

        List<InputStream> inputStreamList;

        public DrainerInputStreamForPatternMatching(List<InputStream> inputStreamList) {
            this.inputStreamList = inputStreamList;
        }

        @Override
        public void run() {
            int value = unfoundPatternsCount.decrementAndGet();
            System.out.println(value);

        }
    }
}
