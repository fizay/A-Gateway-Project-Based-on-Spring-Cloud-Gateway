package com.fizay.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fizay.annotation.Cache;
import com.fizay.util.JsonObjectTransferUtil;

import redis.clients.jedis.Jedis;

/**
 * 采用AOP环绕通知，先查询redis，若没有缓存则让后台微服务方法执行，并将响应结果缓存
 * @author FUZIYAN —— 2020/8/10
 *
 */
@Component
@Aspect
public class RedisAspect {

	//requeired = false,启动时不加载，使用时注入Jedis对象
	@Autowired(required = false)
	private Jedis jedis;

	/**
	 * 
	 * @param joinPoint	后台微服务(Provider)的方法
	 * @param cache	自定义的注解，获取Cache注解的key()属性
	 * @return	返回后台微服务响应结果;该结果可能来自redis缓存,也可能是服务后的结果
	 */
	@Around("@annotation(cache)")
	public Object around(ProceedingJoinPoint joinPoint,Cache cache) {
		
		//ֵ获得方法处@Cache注解的key()属性
		String key = getKey(joinPoint, cache);
		
		//尝试从Redis中根据key获取value
		String result = jedis.get(key);
		
		Object data = null;
		try {
			if(StringUtils.isEmpty(result)) {

				//如果redis中没有缓存结果，则利用aop执行后台微服务的方法获取响应结果
				data = joinPoint.proceed();
				String json = JsonObjectTransferUtil.toJSON(data);
				//并将响应结果放入redis缓存
				jedis.set(key, json);
				System.out.println("该结果来自后台微服务实时响应!!!");
			}else {
				//如果redis中有缓存结果，则直接获取该结果
				data = result;
				System.out.println("该结果来自AOP动态获取redis缓存!!!");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return data;
	}

	/**
	 * 
	 * @param joinPoint	连接点
	 * @param cache	自定义的Cache注解
	 * @return	返回向redis查询缓存的key——/请求路径/用户/请求参数
	 */
	private String getKey
	(ProceedingJoinPoint joinPoint, Cache cache) {

		//通过joinPoint获取后台微服务方法的请求参数
		Object[] requestParams = joinPoint.getArgs();
		StringBuilder strBuilder = new StringBuilder();
		for(Object ele: requestParams) {
			strBuilder.append(ele).append("/");
		}
		//拼接key
		return cache.key() + "/" + strBuilder.toString();
	}
}

