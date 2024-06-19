package com.ram.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample {

    private static int[] array = new int[10];
    private static int numberOfThreads = 2;
    private static int sum = 0;
    private static ReentrantLock mutex = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        for(int i=0; i < array.length;i++) {
            array[i] = 10;
        }

        List<Thread> threads = new ArrayList<>();
        int threadSlice = array.length/numberOfThreads;

        for(int i=0; i < numberOfThreads;i++) {
            Thread thread = new Thread(new MyThread(i * threadSlice, (i+1) * threadSlice));
            thread.start();
            threads.add(thread);
        }

        for(Thread t: threads) {
            t.join();
        }
        System.out.println("Result sum:" + sum);
    }

    static class MyThread implements Runnable {

        private int left;
        private int right;

        public MyThread(int left, int right) {
            this.left = left;
            this.right = right;
        }
        @Override
        public void run() {
            for(int i=left; i < right; i++) {
                mutex.lock();
                sum += array[i];
                System.out.println(mutex.getHoldCount() + " "  + mutex.isLocked());
                mutex.unlock();
                System.out.println(mutex.getHoldCount() + " "  + mutex.isLocked());
            }
        }
    }
}
