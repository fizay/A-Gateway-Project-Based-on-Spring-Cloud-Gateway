package com.fizay.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取properties/whiteIpAddress.properties文件中配置的ip白名单，封装在String ip中，并提供外部获取ip的方法getIps()
 * @author FUZIYAN
 * 
 * 2020/8/7
 *
 */
@Component
@PropertySource(value = {"classpath:/properties/whiteIpAddress.properties"})
public class WhiteIpConfiguration {
	
	@Value("${whiteIps}")
	private String ip;
	
	public String[] getIps() {
		return ip.split(",");
	}
	
	@Override
	public String toString() {
		return "WhiteIpConfiguration [ip=" + ip + "]";
	}
	
	
}
