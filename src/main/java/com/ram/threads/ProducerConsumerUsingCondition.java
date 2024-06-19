package com.ram.threads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerUsingCondition {

    private static Lock lock = new ReentrantLock();
    private static Condition condition1 = lock.newCondition();
    private static Condition condition2 = lock.newCondition();
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        Thread producerThread = new Thread(new Producer(queue));
        Thread consumerThread = new Thread(new Consumer(queue));
        producerThread.start();
        consumerThread.start();

    }
    static class Producer implements  Runnable {

        private Queue<String> queue;

        public Producer(Queue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    System.out.println("I am back");
                    produceData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void produceData() throws InterruptedException {
           lock.lock();
            if (queue.size() == 10) {
                System.out.println("Queue is full, Producer waiting for Consumer to consume..");
                condition1.await();
            }

            Thread.sleep(1000);
            queue.add("element_" + queue.size());
            System.out.println("Producer adding element_" + queue.size());
            if(queue.size() == 1) {
                System.out.println("Producer data, in if");
                condition1.signal();
            }
            lock.unlock();
        }
    }


    static class Consumer implements Runnable {
        private final Queue<String> queue;
        public Consumer(Queue queue) {
            this.queue =  queue;
        }

        @Override
        public void run() {
            while(true) {
                try {
                    consumeData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private  void consumeData() throws InterruptedException {
            lock.lock();
            if (queue.isEmpty()) {
                System.out.println("Queue is empty, Consumer waiting for Producer to produce");
                condition1.await();
            }

            Thread.sleep(700);
            String data = queue.remove();
            System.out.println("Consumed data: " + data);
            if(queue.size() == 9) {
                System.out.println("Consumer data, in if");
                condition1.signal();
            }
            lock.unlock();
        }
    }
}



