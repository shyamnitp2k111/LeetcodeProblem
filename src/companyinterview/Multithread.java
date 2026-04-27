package companyinterview;

import java.util.concurrent.atomic.AtomicInteger;

public class Multithread extends Thread{

    private AtomicInteger atomicInteger;

    public Multithread(AtomicInteger atomicInteger, int val) {
        this.atomicInteger = atomicInteger;
        atomicInteger.set(val);
    }

    @Override
    public void run() {
        //Increase the value
        atomicInteger.getAndIncrement();
        atomicInteger.getAndDecrement();
    }


    public static void main(String[] args) {
        Multithread multithread = new Multithread(new AtomicInteger(), 1);
        multithread.start();
        System.out.println(multithread.atomicInteger);
    }
}
