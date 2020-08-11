package com.fizay.test.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

/**
 * 测试AtomicInteger的getAndIncrement()方法
 * 经测试，多线程并发访问可以获得最终正确的访问量；
 * @author FUZIYAN
 *
 */
public class TestAtomicGetAndIncrement {
	private static AtomicInteger count = new AtomicInteger(0);

	/*
	 * 测试多线程调用increment(),最终能否正确获取count值
	 */
	@Test
	public void testAtomicGetAndIncrement() {
		Thread[] threads= new Thread[100];
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    TestAtomicGetAndIncrement.increment();
                }
            });
            threads[j] = t;
            t.start();
        }
        //让Test线程sleep，然后打印最终的count值
        try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.print("最终的count值为~" + TestAtomicGetAndIncrement.count);
	}
	
	public static void increment() {
		count.getAndIncrement();
	}
}
