package com.fizay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Provider01Application {

	public static void main(String[] args) {
		SpringApplication.run(Provider01Application.class, args);
	}

}
