package company.wissen;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class MultiThreadHandle {
    public synchronized void test1() {
        System.out.println("Inside Test1 Method");
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static synchronized void test2() {
        System.out.println("Inside Test2 Method");
        while (true) {
            try {
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
 
public class ThreadTest {
    public static void main(String args[]) {
        MultiThreadHandle obj =  new MultiThreadHandle();
        ExecutorService executor = Executors.newFixedThreadPool(10);
 
        Runnable runnableTask1 = () -> { obj.test1(); };
        Runnable runnableTask2 = () -> { obj.test2(); };
 
        executor.execute(runnableTask1);
        executor.execute(runnableTask2);
    }
}
