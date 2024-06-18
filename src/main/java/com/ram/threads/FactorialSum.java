package com.ram.threads;

public class FactorialSum {

    private static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        int n = 5;
        ThreadGroup threadGroup = new ThreadGroup("group1");
        for(int i=1; i <= n;i++) {
            Thread t = new Thread(threadGroup,new CalcFactorialThread(i));
            t.start();
            System.out.println("Thread " + t.getName() + " is in "  + t.getState());
            t.join();
            System.out.println("Thread " + t.getName() + " is in "  + t.getState());
        }

        System.out.println("Calculated factorial sum for : " + n + " is sum: "  + sum);

    }

    static class CalcFactorialThread implements  Runnable {
        int num;
        public CalcFactorialThread(int num) {
            this.num = num;
        }
        @Override
        public void run() {
            int result =  factorial(num);
            System.out.println("Thread = " + Thread.currentThread().getName() + " is calculating factorial for : " + num + " is : " + result);
            sum += result;
        }
    }

    private static int factorial(int n) {
        int result = 1;
        for(int i=1;i <= n;i++) {
            result = result * i;
        }
        return result;
    }
}
