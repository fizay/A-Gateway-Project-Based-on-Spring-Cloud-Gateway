package com.fizay.gateway.mail;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.fizay.util.MailUtil;
import com.fizay.util.ReadTomcatLogUtil;

/**
 * 读取Tomcat打印日志
 * 筛选其中非200的日志记录
 * 邮件发送至管理员1045541239@qq.com
 * 定时每日下午6:00发送
 * @author FUZIYAN
 * 
 * 2020/8/11
 *
 */
@Component
public class SendMail extends QuartzJobBean {
	private Date date;
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		//利用定时功能，定时读取tomcat日志，并发送邮件至管理员FUZIYAN
		date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String wanted = "500";
		String pathProvider01 = "C:/Java/SummerTraining/st-provider01/access_log.";
		String resultProvider01 = ReadTomcatLogUtil.readWantedText(pathProvider01 + format.format(date).toString() + ".log", wanted);
		
		String PathProvider02 = "C:/Java/SummerTraining/st-provider02/access_log.";
		String resultProvider02 = ReadTomcatLogUtil.readWantedText(PathProvider02 + format.format(date).toString() + ".log", wanted);
	
		String PathSSO = "C:/Java/SummerTraining/st-sso/access_log.";
		String resultSSO = ReadTomcatLogUtil.readWantedText(PathSSO + format.format(date).toString() + ".log", wanted);
		
		String PathWeb = "C:/Java/SummerTraining/st-web/access_log.";
		String resultWeb = ReadTomcatLogUtil.readWantedText(PathWeb + format.format(date).toString() + ".log", wanted);
		
		StringBuilder builder = new StringBuilder();
		if(resultProvider01 != null) {
			builder.append(resultProvider01);
		}
		if(resultProvider02 != null) {
			builder.append(resultProvider02);
		}
		if(resultSSO != null) {
			builder.append(resultSSO);
		}
		if(resultWeb != null) {
			builder.append(resultWeb);
		}
		String result = builder.toString();
		MailUtil mail;
		try {
			mail = new MailUtil();
			if(result != null) {
				mail.sendMail(result);
			}else {
				mail.sendMail("今天一切正常");
			}
			
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		System.out.println("----------------------------今日邮件已发送----------------------------");
	}
}
