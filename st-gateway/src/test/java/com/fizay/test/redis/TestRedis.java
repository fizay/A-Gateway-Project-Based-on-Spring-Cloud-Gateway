package com.fizay.test.redis;

import org.junit.jupiter.api.Test;

/*
 * 测试是否正确连接Linux下的redis
 */
public class TestRedis {
	
	@Test
	public void testRedis() {
		redis.clients.jedis.Jedis jedis = new redis.clients.jedis.Jedis("192.168.2.131", 6379);
		jedis.set("redis", "FUZIYAN");
		System.out.println("获取redis中的数据:"+jedis.get("redis"));
		jedis.close();
	}
}
