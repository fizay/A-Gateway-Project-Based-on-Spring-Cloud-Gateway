package com.fizay.gateway.config;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fizay.gateway.filter.SpecifiedQpsFilter;


/**
 * 
 * 使用Quartz对SpecifiedQpsFilter.class中的原子类count定时重置，暂时设定为1min
 * 
 * @author FUZIYAN
 *
 */
@Configuration
public class QuartzSpecifiedQpsConfiguration {

	//定义任务详情
	@Bean
	public JobDetail resetAtomicCountJob() {
		//指定job的名称和持久化保存任务
		return JobBuilder
				.newJob(SpecifiedQpsFilter.class)
				.withIdentity("specifiedQpsFilter")
				.storeDurably()
				.build();
	}
	//定义触发器，设置任务执行间隔
	@Bean
	public Trigger resetAtomicCountTrigger() {
		CronScheduleBuilder scheduleBuilder 
		= CronScheduleBuilder.cronSchedule("0 */1 * * * ?");	//每日凌晨00:30分的表达式为-->>0 30 0 * * ?
		return TriggerBuilder
				.newTrigger()
				.forJob(resetAtomicCountJob())
				.withIdentity("specifiedQpsFilter")
				.withSchedule(scheduleBuilder).build();
	}
}
