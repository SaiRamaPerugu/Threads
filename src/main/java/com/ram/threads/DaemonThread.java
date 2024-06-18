package com.ram.threads;

public class DaemonThread {

    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyThread(5), "thread1");
        Thread thread2 = new Thread(new MyThread(1), "thread2");
        thread1.setDaemon(true);
        thread1.start();
        thread2.start();
    }

    static class MyThread implements Runnable {
        int numberOfseconds;
        public MyThread(int numberOfseconds) {
            this.numberOfseconds = numberOfseconds;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " started");
            try {
                for(int i=numberOfseconds;i>0;i--) {
                    System.out.println(Thread.currentThread().getName() + "Sleeping for 1 second");
                    Thread.sleep( 1000);
                }
            } catch(InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
