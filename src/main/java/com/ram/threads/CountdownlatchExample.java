package com.ram.threads;

import java.util.concurrent.CountDownLatch;

public class CountdownlatchExample {

    private static int foundPosition = 0;
    private static int numberOfThreads = 2;
    private static int searchNumber = 3;
    private static int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private static CountDownLatch countDownLatch = new CountDownLatch(numberOfThreads);
    public static void main(String[] args) throws InterruptedException {

        int threadSlice = arr.length / numberOfThreads;

        for(int i=0; i < numberOfThreads;i++) {
            Thread thread = new Thread(new WorkerThread(i * threadSlice, (i + 1) * threadSlice), "Thread " + i);
            thread.start();
        }

        //countDownLatch.await();
    }

    static  class WorkerThread implements Runnable {
        private final int left;
        private final int right;
        public WorkerThread(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void run() {
            for(int i = left; i < right; i++ ) {
                if(arr[i] == searchNumber) {
                    foundPosition = i;
                    System.out.println("Found value by : " + Thread.currentThread().getName() + " at position: " + i);
                }
            }
            countDownLatch.countDown();
        }
    }
}
