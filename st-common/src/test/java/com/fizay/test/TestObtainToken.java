package com.fizay.test;

import org.junit.jupiter.api.Test;

import com.fizay.util.JsonObjectTransferUtil;
import com.fizay.vo.User;

/**
 * 生成用户信息的JSON
 * 用于jmeter和curl中的请求头信息
 * @author FUZIYAN
 *
 * 2020/8/7
 */
public class TestObtainToken {

	/*
	 * FUZIYAN,id=1,拥有cmsoft:root权限，可以访问/getInfo和/authLimit
	 * 
	 * curl http://localhost:3001/provider-service/getInfo -H "CMSOFT_TOKEN:{\"id\":1,\"username\":\"FUZIYAN\",\"salt\":\"root\",\"email\":\"microfzy@outlook.com\",\"phone\":\"15700756797\",\"qpsLevel\":100,\"deptId\":1,\"note\":\"none note\"}"
	 */
	@Test
	public void testObtainFUZIYANToken() {
		User user = new User();
		user.setDeptId(1);
		user.setEmail("microfzy@outlook.com");
		user.setId(1);
		user.setNote("none note");
		user.setPhone("15700756797");
		user.setQpsLevel(100);
		user.setSalt("root");
		user.setUsername("FUZIYAN");
		String CMSOFT_TOKEN = JsonObjectTransferUtil.toJSON(user);
		System.out.println(CMSOFT_TOKEN);
	}

	/*
	 * ZHANGSAN,id=2,拥有cmsoft:view权限，可以访问/getInfo
	 */
	@Test
	public void testObtainZAHNGSANToken() {
		User user = new User();
		user.setDeptId(1);
		user.setEmail("jacob2014@126.com ");
		user.setId(2);
		user.setNote("none note");
		user.setPhone("18717023757 ");
		user.setQpsLevel(50);
		user.setSalt("customer ");
		user.setUsername("ZHANGSAN");
		String CMSOFT_TOKEN = JsonObjectTransferUtil.toJSON(user);
		System.out.println(CMSOFT_TOKEN);
	}

}
