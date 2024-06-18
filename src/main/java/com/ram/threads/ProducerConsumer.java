package com.ram.threads;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumer {

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
                    produceData();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private void produceData() throws InterruptedException {
            synchronized (queue) {
                if (queue.size() == 10) {
                    System.out.println("Queue is full, Producer waiting for Consumer to consume..");
                    queue.wait();
                }

                Thread.sleep(1000);
                queue.add("element_" + queue.size());
                System.out.println("Producer adding element_" + queue.size());
                if(queue.size() == 1) {
                    queue.notify();
                }
            }
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
            synchronized (queue) {
                if (queue.isEmpty()) {
                    System.out.println("Queue is empty, Consumer waiting for Producer to produce");
                }

                Thread.sleep(1000);
                String data = queue.remove();
                System.out.println("Consumed data: " + data);
                if(queue.size() == 9) {
                    queue.notify();
                }
            }
        }
    }
}



