package com.fizay.gateway.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fizay.gateway.filter.TotalStreamLimit;

/**
 * 
 * 使用Quartz对TotalStreamLimit.class中的原子类count定时重置，暂时设定为2min
 * 
 * @author FUZIYAN
 *
 */
@Configuration
public class QuartzTotalSteamConfiguration {
	
	//定义任务详情
		@Bean
		public JobDetail resetAtomicCountAndDateJob() {
			//指定job的名称和持久化保存任务
			return JobBuilder
					.newJob(TotalStreamLimit.class)
					.withIdentity("totalStreamLimit")
					.storeDurably()
					.build();
		}
		//定义触发器，设置任务执行间隔
		@Bean
		public Trigger resetAtomicCountAndDateTrigger() {
			CronScheduleBuilder scheduleBuilder 
			= CronScheduleBuilder.cronSchedule("0 */2 * * * ?");	//每日凌晨00:30分的表达式为-->>0 30 0 * * ?
			return TriggerBuilder
					.newTrigger()
					.forJob(resetAtomicCountAndDateJob())
					.withIdentity("totalStreamLimit")
					.withSchedule(scheduleBuilder).build();
		}
}
