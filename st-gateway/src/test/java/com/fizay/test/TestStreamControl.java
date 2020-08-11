package com.fizay.test;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * https://blog.csdn.net/qq447995687/article/details/84873717
 * 控制同一时刻并发的线程数量，每次只能有10个线程能够同时访问，达到了流量控制目的
 */
public class TestStreamControl {
	
    private static Semaphore semaphore = new Semaphore(10);

    void toDo() {
        if (!semaphore.tryAcquire()) {
            return;
        }
        try {
        	//---------------------------do something-----------------------
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //---------------------------------------------------------------------
        } finally {
            semaphore.release();
        }
    }

    private static AtomicInteger atomicInteger = new AtomicInteger(1);

    void toAdd() {
        int count = atomicInteger.get();
        if (count > 10) {
            return;
        }
        if (!atomicInteger.compareAndSet(count, count + 1)) {
            return;
        }
        //---------------------------do something-----------------------
		System.out.println(Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //---------------------------------------------------------------------
        atomicInteger.decrementAndGet();
    }

    public static void main(String[] args) {
        final TestStreamControl f = new TestStreamControl();
        for (int i = 0; i < 10; i++) {
            Thread[] threads = new Thread[20];
            for (int j = 0; j < 20; j++) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        f.toDo();
                    }
                });
                threads[j] = t;
                t.start();
            }
            for (int j = 0; j < 20; j++) {
                try {
                    threads[j].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("-----------------");
        }
    }
}


