package com.fizay.gateway.filter;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import reactor.core.publisher.Mono;

/**
 * order -1，统计网关总流量
 * 利用Quartz每天24:00更新数据, 暂时2min更新一次看效果
 * @author FUZIYAN
 *
 */
@Component
public class TotalStreamLimit extends QuartzJobBean implements GlobalFilter, Ordered {
	private static volatile AtomicInteger count = new AtomicInteger(0);	//网关总访问路存redis，服务器重启后从先redis中取值
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");	//format到毫秒：HH:mm:ss:SSS
	private Date date = new Date();
	
	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		count.getAndIncrement();
		if(count.get() <= 1000) {	//设置单天访问量最大不超过1000次, 暂时固定
			System.out.println("今日"+ dateFormat.format(date) +"网关访问请求总数达到了 -->>" + count.get());
			return chain.filter(exchange);
		}
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.TOO_MANY_REQUESTS);	//429
		return response.setComplete();
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		//更新日期
		date = new Date();
		//重置计数器count
		count = new AtomicInteger(0);
		
		System.out.println("---------------单日访问限制已重置---------------");
	}

}
