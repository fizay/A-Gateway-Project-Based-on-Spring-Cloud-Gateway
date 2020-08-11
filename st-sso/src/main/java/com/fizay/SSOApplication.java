package com.fizay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@MapperScan("com.cmsoft.dao")
@EnableDiscoveryClient
@SpringBootApplication
public class SSOApplication {

	public static void main(String[] args) {
		SpringApplication.run(SSOApplication.class, args);
	}

}
