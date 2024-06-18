package com.ram.threads;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedExample {

    private static int globalCounter = 0;
    private static final Object obj = new Object();

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        ThreadGroup group = new ThreadGroup("group1");
        for(int i=1; i <= 1000; i++) {
            Thread t = new Thread(group, new MyThread());
            t.start();
            threads.add(t);
        }

        group.interrupt();
        threads.forEach(t -> {
            try {
                t.join();
            } catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        System.out.println("Total=" + globalCounter);
    }

    static class MyThread implements  Runnable {

        @Override
        public void run() {
            System.out.println("Thread name:" + Thread.currentThread().getName());
            try {
                Thread.sleep(10000);
            } catch(InterruptedException ex) {
                System.out.println(Thread.currentThread().getName() + " interrupted.");
            }
            synchronized (obj) {
                globalCounter++;
            }
        }
    }
}
