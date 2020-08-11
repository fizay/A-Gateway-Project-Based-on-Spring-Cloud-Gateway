package com.fizay.util;

import com.fizay.vo.User;

/**
 * 本地线程，可以在同一个线程内实现数据共享
 * @author FUZIYAN —— 2020/8/3
 *
 */
public class ThreadLocalUtil {
	
	private static ThreadLocal<User> localThread = new ThreadLocal<>();
	
	public static void set(User user) {
		localThread.set(user);
	}
	
	public static User get() {
		return localThread.get();
	}
	
	public static void remove() {
		localThread.remove();
	}
}
