package com.fizay.gateway.filter;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * AuthenticationFilter验证登录后，还需验证用户是否具有访问权限
 * 从token中取出用户具有的permissions
 * 查询访问路径需要的permissions
 * 比对 
 * 优化：不能总是从数据库查询，使用缓存
 * @author FUZIYAN
 *
 */
@Component
public class PermissionFilter implements GlobalFilter, Ordered{
	@Override
	public int getOrder() {
		return 3;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if("/index".equals(exchange.getRequest().getURI().getPath())) {
			return chain.filter(exchange);
		}
		
		//permissions = ["cmsoft:root","cmsoft:add","cmsoft:delete","cmsoft:update","cmsoft:view"]
		//getPath() = /getInfo
		String perStr = exchange.getRequest().getHeaders().getFirst("permissions");
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
		String[] permissions = perStr.substring(1, perStr.length() - 1).split(",");
		for(String per: permissions) {
			map.put(per, "value");	//per like "cmsoft:view"
		}
		
		//通过用户访问路径getPath()查询资源id，获得访问资源需要的权限，检查是否在map的key中
		CloseableHttpClient client = HttpClients.createDefault();
		String httpUrl = "http://localhost:8001/findResourcePermission?path=" + exchange.getRequest().getURI().getPath();
		HttpGet httpGet = new HttpGet(httpUrl);
		ServerHttpResponse response = null;
		String result = null;
		try {
			CloseableHttpResponse httpResponse = client.execute(httpGet);
			if(httpResponse.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = httpResponse.getEntity();
				result = EntityUtils.toString(entity);
			}else {
			    response = exchange.getResponse();
				response.setStatusCode(HttpStatus.GATEWAY_TIMEOUT);	//504
				return response.setComplete();
			}
		} catch (Exception e) {
			response = exchange.getResponse();
			response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);	//406
			return response.setComplete();
		}
		
		//如果用户拥有的权限中不含访问路径需要的权限，则返回401
		if(!map.containsKey(result)) {
			response = exchange.getResponse();
			response.setStatusCode(HttpStatus.UNAUTHORIZED);	//401
			return response.setComplete();
		}
		
		//用户具备访问权限，放行
		return chain.filter(exchange);

	}
}
