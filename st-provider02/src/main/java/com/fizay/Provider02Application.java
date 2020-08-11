package com.fizay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Provider02Application {

	public static void main(String[] args) {
		SpringApplication.run(Provider02Application.class, args);
	}

}
