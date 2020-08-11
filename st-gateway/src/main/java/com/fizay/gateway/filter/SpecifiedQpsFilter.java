package com.fizay.gateway.filter;

import java.util.concurrent.atomic.AtomicInteger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import com.google.common.util.concurrent.RateLimiter;
import reactor.core.publisher.Mono;

/**
 * 根据用户的qpsLevel来限流
 * qps==100,不限流
 * qps==50,限流50%
 * qps==0,完全限流
 * 
 * 使用Quartz定时每日24:00重置
 * 
 * @author FUZIYAN
 *
 */
@Component
public class SpecifiedQpsFilter extends QuartzJobBean implements GlobalFilter, Ordered {
	ServerHttpResponse response = null;
	public static final RateLimiter limiter = RateLimiter.create(1); // 允许每秒最多1个任务
	private static volatile AtomicInteger count = new AtomicInteger(0);;
	
	@Override
	public int getOrder() {
		return 4;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if("/index".equals(exchange.getRequest().getURI().getPath())) {
			return chain.filter(exchange);
		}
		
		//AuthenticationFilter中将用户的qpsLevel属性放入请求头
		String qpsLevelStr = exchange.getRequest().getHeaders().getFirst("qpsLevel");
		Integer qpsLevel = Integer.parseInt(qpsLevelStr);
		if(qpsLevel == 100) {
			return chain.filter(exchange);
		}else if(qpsLevel == 50) {
			count.incrementAndGet();
			if(count.get() > 10) {	//针对特定用户的单天访问总量,暂时设置为10
				response = exchange.getResponse();
				response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);	//429
				return response.setComplete();
			}
			limiter.acquire();
			return chain.filter(exchange);
		}else {	//qpsLevel == 0
			response = exchange.getResponse();
			response.setStatusCode(HttpStatus.FORBIDDEN);	//403
			return response.setComplete();
		}
	}
	
	/*
	 * Quartz定时重置count
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		if(count.get() > 10) {
			count = new AtomicInteger(0);
			System.out.println("---------------特定客户的单日访问限制已重置---------------");
		}
		
	}

}
