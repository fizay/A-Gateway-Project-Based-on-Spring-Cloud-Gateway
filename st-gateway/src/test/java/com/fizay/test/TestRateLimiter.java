package com.fizay.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.util.concurrent.RateLimiter;

/*
 * 当线程拿到桶中的令牌时，才可以执行。通过设置每秒生成的令牌数来控制速率
 * https://www.cnblogs.com/pcheng/p/8127040.html
 */
public class TestRateLimiter implements Runnable {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final RateLimiter limiter = RateLimiter.create(1); // 允许每秒最多1个任务

    public static void main(String[] arg) {
        for (int i = 0; i < 11; i++) {
            limiter.acquire(); // 请求令牌,超过许可会被阻塞
            Thread t = new Thread(new TestRateLimiter());
            t.start();
        }
    }

	@Override
	public void run() {
		System.out.println(sdf.format(new Date()) + " Task End..");
	}

}
