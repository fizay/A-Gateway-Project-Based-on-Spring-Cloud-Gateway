package com.fizay.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fizay.gateway.resolver.GlobalKeyResolver;

/**
 * 配置类，返回一个实现了KeyResolver的对象。Redis利用该对象可以实现基于ip、url、user的限流
 * @author FUZIYAN
 *
 */
@Component
public class GatewayRateLimitConfiguration {
	
	@Bean
	public GlobalKeyResolver globalKeyResolver() {
		return new GlobalKeyResolver();
	}
}
