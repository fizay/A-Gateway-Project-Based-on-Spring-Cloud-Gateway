package com.fizay.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fizay.controller.WebController;

/**
 * 指定定时任务位于WebController.class(override的executeInternal()方法)
 * 因为spring会将类名首字母小写后作为Bean name，所以withIdentity放入(authenticationFilter)
 * 定义触发器，设置为每隔5s执行一次定时任务
 * @author FUZIYAN
 *
 */
@Configuration
public class QuartzPVConfiguration {
	
		//定义任务详情
		@Bean
		public JobDetail savePVJobDetail() {
			//指定job的名称和持久化保存任务
			return JobBuilder
					.newJob(WebController.class)
					.withIdentity("webController")
					.storeDurably()
					.build();
		}
		//定义触发器，设置任务执行间隔
		@Bean
		public Trigger savePVTrigger() {
			CronScheduleBuilder scheduleBuilder 
				= CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
			return TriggerBuilder
					.newTrigger()
					.forJob(savePVJobDetail())
					.withIdentity("authenticationFilter")
					.withSchedule(scheduleBuilder).build();
		}

}
