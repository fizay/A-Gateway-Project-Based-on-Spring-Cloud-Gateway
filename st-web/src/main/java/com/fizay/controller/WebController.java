package com.fizay.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.xjs.ezprofiler.annotation.Profiler;

import redis.clients.jedis.Jedis;

@Controller
public class WebController  extends QuartzJobBean {
	private static volatile AtomicInteger count;
	private static Jedis jedis = new Jedis("192.168.2.131", 6379);
	
	//服务器重启后，先从redis中获取之前保存的访问量记录。如果没有，则访问量从1开始
	static {
		try {
			int preCount = Integer.parseInt(jedis.get("pv").trim());
			count = new AtomicInteger(preCount);
		} catch (Exception e) {
			count = new AtomicInteger(0);
		}
	}
	
	@Profiler
	@RequestMapping("/index")
	public String index(Integer i) {
		if(i == null) {
			throw new RuntimeException();	//全局异常处理类处理RuntimeException
		}
		count.getAndIncrement();
		System.out.println("网关记录的访问量达到了~" + count.get());
		return "index";
	}
	
	@RequestMapping("/login/{path}")
	public String login(@PathVariable String path, Model model) {
		//未登录前，访问http://localhost:3001/authLimit会受限，并被gateway重定向至此方法
		//测试是否能得到“/authLimit”—— yes！
		//System.out.println(path);
		
		//将登录前的请求路径通过model带入jsp中
		model.addAttribute("preRequestPath", path);
		return "login";
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		jedis.set("pv", count.get() + "");
	}
	
}
