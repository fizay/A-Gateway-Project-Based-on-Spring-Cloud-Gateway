package com.fizay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

/**
 * 配置redis客户端服务，可通过@Autowired自动注入Jedis对象
 * @author FUZIYAN
 *
 */
@Configuration
public class CustomerJedis {
	
	@Bean
	public Jedis getJedis() {
		return new Jedis("192.168.2.131", 6379);
	}
}
