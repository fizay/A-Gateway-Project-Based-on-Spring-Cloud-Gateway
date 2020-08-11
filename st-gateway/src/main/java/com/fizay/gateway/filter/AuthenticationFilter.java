package com.fizay.gateway.filter;

import java.util.function.Consumer;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fizay.util.JsonObjectTransferUtil;
import com.fizay.vo.User;

import reactor.core.publisher.Mono;

/**
 * 身份验证过滤器，从http请求头中获取CMSOFT_TOKEN,并尝试将其转化为User对象
 * 转换失败，意味着请求头无效，需重新登录
 * 转换成功，获取用户的权限信息，并放入请求头中到达下一个filter
 * 
 * @author FUZIYAN
 *
 */
@Component
public class AuthenticationFilter implements GlobalFilter, Ordered{

	@Override
	public int getOrder() {
		return 2;
	}
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if("/index".equals(exchange.getRequest().getURI().getPath())) {	//对/index放行
			return chain.filter(exchange);
		}
		String token = exchange.getRequest().getHeaders().getFirst("CMSOFT_TOKEN");
		User user;
		String path = null;
		String url = null;
		ServerHttpResponse response = null;
		try {
			user = JsonObjectTransferUtil.toObject(token, User.class);
		}catch(Exception e) {	//如果不能将CMSOFT_TOKEN中的字符串转化成User对象，说明请求头无效，重定向到登录页面
			e.printStackTrace();
			path = exchange.getRequest().getURI().getPath();
			url = "http://localhost:6001/login" + path;
			response = exchange.getResponse();
			//状态码应该为303 参考https://www.liangzl.com/get-article-detail-18971.html
			response.setStatusCode(HttpStatus.SEE_OTHER);	
			response.getHeaders().set(HttpHeaders.LOCATION, url);
			return response.setComplete();
		}
		
		//通过用户id查询具有的权限，放入请求头中传递到下一个filter
		CloseableHttpClient client = HttpClients.createDefault();
		String httpUrl = "http://localhost:8001/findUserPermissions?id=" + user.getId();
		HttpGet httpGet = new HttpGet(httpUrl);
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
		String permissions = result;	//匿名内部类和局部内部类只能引用外部的fianl变量！！在使用前赋值给一个新的变量，这样java8会认为这个新的变量是final
		Consumer<HttpHeaders> httpHeaders = httpHeader -> {
			httpHeader.set("permissions", permissions);
			httpHeader.set("qpsLevel", user.getQpsLevel() + "");
		};
		ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate().headers(httpHeaders).build();
		exchange.mutate().request(serverHttpRequest).build();

		return chain.filter(exchange);

	}
}
