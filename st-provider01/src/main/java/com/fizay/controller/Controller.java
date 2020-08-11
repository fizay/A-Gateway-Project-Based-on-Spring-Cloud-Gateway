package com.fizay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fizay.annotation.Cache;
import com.github.xjs.ezprofiler.annotation.Profiler;

@RestController(value = "findCustomerAnnotation")	
public class Controller {							
	
	//AOP获取参数，拼接为redis的key(针对不同用户的不用请求参数，key值变化)，value就是getInfo的响应
	@Cache(key = "/getInfo")
	@Profiler
	@RequestMapping("/getInfo")
	public String getInfo(@RequestParam("username")String username, @RequestParam("para")String para) {
		return "hello "+ username + ", This is Provider01!"+ " your requestparam is: " + para;
	}
	
	//AOP动态查询redis缓存
	@Cache(key = "/authLimit")
	@Profiler
	@RequestMapping("/authLimit")
	public String limit() {
		return "你好 " + ", 你有访问权限——Provider01！";
	}
	
}
