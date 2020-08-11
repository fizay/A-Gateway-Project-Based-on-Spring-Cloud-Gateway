package com.fizay.gateway.route;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.ipresolver.RemoteAddressResolver;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fizay.gateway.config.WhiteIpConfiguration;
import com.fizay.gateway.resolver.GlobalKeyResolver;

import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;

/**
 * 使用java编码的方式，替换原先yml中路由2的配置：
 * #考虑到要采用ip白名单，将路由2改为java配置      
      - id: accessProvider   #路由2：要想访问后台微服务，请求头中就必须要含有CMSOFT_TOKEN才能实现路由转发！！
        uri: lb://provider
        predicates:
        - Path=/provider-service/**
        - RemoteAddr=127.0.0.1,0:0:0:0:0:0:0:1 #后者是ipv6格式的本机ip
       #加上这个我就没办法跳转登录页面了--前台访问http://localhost:3001/provider-service/getInfo没有带请求头根本匹配不到这个路由
       #debug跑了一下，连order为-1的TotalStreamLimit都没进入
       # - Header=CMSOFT_TOKEN,[\s\S]* #正则表达，匹配任意字符串
        filters:
        - StripPrefix=1   #去掉Path中的/provider-service
        - name: RequestRateLimiter  #这个名字不能动
          args:
            key-resolver: '#{@globalKeyResolver}'
            redis-rate-limiter.replenishRate: 10
            redis-rate-limiter.burstCapacity: 10
 * @author FUZIYAN
 *
 */
@Component
public class AccessProvider {
	
	@Autowired(required = true)
	private WhiteIpConfiguration whiteIps;

	@Autowired
	GlobalKeyResolver globalKeyResolver;
	
	RemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);
	
//	private static final String[] whiteAddress={
//			"127.0.0.1","0:0:0:0:0:0:0:1","192.168.0.1"
//	};	//代码写死，不利于后期配置维护和更改。采用@PropertySource搭配@Value从.properties文件动态取值
	
	
	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		String[] whiteAddress = whiteIps.getIps();
		return builder.routes().route("accessProvider", r -> r.path("/provider-service/**").and()
				.remoteAddr(resolver, whiteAddress).filters(f -> f.stripPrefix(1).requestRateLimiter()
				.rateLimiter(RedisRateLimiter.class, c -> c.setBurstCapacity(10).setReplenishRate(10))
				.configure(c -> c.setKeyResolver(globalKeyResolver))).uri("lb://provider")).build();
	}
}
