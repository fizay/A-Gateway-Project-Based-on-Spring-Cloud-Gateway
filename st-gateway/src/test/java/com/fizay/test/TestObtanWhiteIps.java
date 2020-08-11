package com.fizay.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fizay.gateway.config.WhiteIpConfiguration;

/**
 * 测试读取properties文件获取配置信息
 * @author FUZIYAN
 *
 */
@SpringBootTest	//动态加载spring容器
public class TestObtanWhiteIps {

	@Autowired
	private WhiteIpConfiguration ip;
	
	/*
	 * 测试是否能读取到whiteIpAddress.properties中的ip配置信息
	 */
	@Test
	public void testIp() {
		
		System.out.println(ip);
	}
	

}
