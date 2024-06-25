package com.ram.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    private static int[][] array = {
            {1, 2, 3, 1},
            {2, 1, 2, 1},
            {1, 2, 1, 3},
            {1, 1, 1, 2},
    };

    private static int[][] outputArray = {
            {1, 2, 3, 1},
            {9, 8, 9, 8},
            {35, 36, 35, 37},
            {144, 144, 144, 145},
    };

    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("Barrier has beeen released!"));

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for(int i=0; i < 4; i++) {
            Thread thread = new Thread(new WorkerThread(i));
            threads.add(thread);
            thread.start();
        }

        for(Thread thread: threads) {
            thread.join();
        }

        System.out.println(Arrays.deepToString(array));
    }

    static class WorkerThread implements Runnable {
        private int columnId;

        public WorkerThread(int columnId) {
            this.columnId = columnId;
        }

        @Override
        public void run() {
            for (int i = 1; i < 4; i++) {
                int S = 0;

                for (int j = 0; j < 4; j++) {
                    S = S + array[i - 1][j];
                }
                array[i][columnId] = S + array[i][columnId];
                //array[i][columnId] = array[i][columnId] + S;
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //System.out.println(Arrays.deepToString(array));
        }
    }
}
