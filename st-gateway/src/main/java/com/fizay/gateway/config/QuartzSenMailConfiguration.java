package com.fizay.gateway.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fizay.gateway.mail.SendMail;

/**
 * 利用Quartz定时sendMail任务，收集tomcat日志中500错误记录，并发送至FUZIYAN邮箱
 * @author FUZIYAN
 * 
 *  2020/8/11
 **/
@Configuration
public class QuartzSenMailConfiguration {
	
		//定义任务详情
		@Bean
		public JobDetail useQuartzSendMailJob() {
			//指定job的名称和持久化保存任务
			return JobBuilder
					.newJob(SendMail.class)
					.withIdentity("sendMail")
					.storeDurably()
					.build();
		}
		//定义触发器，设置任务执行间隔
		@Bean
		public Trigger useQuartzSendMailJobTrigger() {
			CronScheduleBuilder scheduleBuilder 
			= CronScheduleBuilder.cronSchedule("0 */5 * * * ?");	//每日凌晨00:30分的表达式为-->>0 30 0 * * ?
			return TriggerBuilder
					.newTrigger()
					.forJob(useQuartzSendMailJob())
					.withIdentity("sendMail")
					.withSchedule(scheduleBuilder).build();
		}
}
