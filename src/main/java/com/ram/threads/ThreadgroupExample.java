package com.ram.threads;

public class ThreadgroupExample {

    public static void main(String[] args) {
        ThreadGroup threadGroup = new ThreadGroup("group1");
        threadGroup.setMaxPriority(10);
        Thread thread1 = new Thread(threadGroup, new MyThread(), "Thread1");
        Thread thread2 = new Thread(threadGroup, new MyThread(), "Thread2");
        Thread thread3 = new Thread(threadGroup, new MyThread(), "Thread3");
        thread1.setPriority(7);
        thread1.start();
        thread2.start();
        thread3.start();

        System.out.println(threadGroup.activeCount());
        threadGroup.interrupt();
    }


    static class MyThread implements Runnable {

        @Override
        public void run() {
            while(true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    System.out.println(Thread.currentThread().getName() + " has priority " + Thread.currentThread().getPriority() +
                            " is in group " + Thread.currentThread().getThreadGroup().getName());
                }
            }
        }
    }
}
