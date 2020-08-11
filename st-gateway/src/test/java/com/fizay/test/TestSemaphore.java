package com.fizay.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Semaphore;

/*
 * 设置信号量总数，当线程拿到信号量，才可以执行，当实行完毕再释放信号量。从而控制接口的并发数量
 * https://www.cnblogs.com/pcheng/p/8127040.html
 */
public class TestSemaphore implements Runnable {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final Semaphore semaphore = new Semaphore(1, true); // 允许并发的任务量限制为5个

    public static void main(String[] arg) {
        for (int i = 0; i < 10; i++) {
             Thread t = new Thread(new TestSemaphore());
             t.start();
        }
    }

    public void run() {
        try {
            semaphore.acquire(); // 获取信号量,不足会阻塞
            System.out.println(sdf.format(new Date()) + " Task Start..");
            Thread.sleep(5000);
            System.out.println(sdf.format(new Date()) + " Task End..");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // 释放信号量
        }
    }
}
