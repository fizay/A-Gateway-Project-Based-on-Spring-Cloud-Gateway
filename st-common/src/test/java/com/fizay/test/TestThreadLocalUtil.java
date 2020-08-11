package com.fizay.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fizay.util.ThreadLocalUtil;
import com.fizay.vo.User;

/**
 * 测试ThreadLocal的存/取操作
 * @author FUZIYAN
 *
 */
public class TestThreadLocalUtil {
	
	@BeforeEach
	public void setThreadLocal() {
		//向ThreadLocal存
		User user = new User();
		user.setId(1);
		user.setUsername("FUZIYAN");
		ThreadLocalUtil.set(user);
	}
	
	@Test
	public void testThreadLocalUtil() {
		//向ThreadLocal取
		User user = ThreadLocalUtil.get();
		System.out.println(user);
	}
}
