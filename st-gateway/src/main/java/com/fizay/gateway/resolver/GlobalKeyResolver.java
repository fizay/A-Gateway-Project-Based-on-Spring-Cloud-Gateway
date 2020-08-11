package com.fizay.gateway.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class GlobalKeyResolver implements KeyResolver{

	@Override
	public Mono<String> resolve(ServerWebExchange exchange) {
		
		//根据访问路径限流
		return Mono.just(exchange.getRequest().getURI().getPath());
	}

}
