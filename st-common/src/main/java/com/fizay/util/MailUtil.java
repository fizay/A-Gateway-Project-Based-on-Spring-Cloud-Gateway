package com.fizay.util;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * 封装的邮件发送工具类
 * 目前代码固定，只能发送至FUZIYAN的邮箱
 * 
 * 需要修改邮箱账号和授权码
 * 
 * @author FUZIYAN
 * 
 * 2020/8/11
 *
 */
public class MailUtil{
	private Properties properties = new Properties();
	private Session session;
	
	public MailUtil() throws GeneralSecurityException {
        properties.setProperty("mail.host","smtp.qq.com");

        properties.setProperty("mail.transport.protocol","smtp");

        properties.setProperty("mail.smtp.auth","true");
        
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("邮箱账号","邮箱SMTP服务授权码");
            }
        });
	}
	
	public void sendMail(String content) throws MessagingException {
		 //开启debug模式
        session.setDebug(true);

        //获取连接对象
        Transport transport = session.getTransport();

        //连接服务器
        transport.connect("smtp.qq.com","邮箱账号","邮箱SMTP服务授权码");

        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);

        //邮件发送人
        mimeMessage.setFrom(new InternetAddress("邮箱账号"));

        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO,new InternetAddress("邮箱账号"));
        
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //邮件标题
        mimeMessage.setSubject(dateFormat.format(date) + "之spring cloud gateway网关超标信息统计");

        //邮件内容
        mimeMessage.setContent(content,"text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();
	}
}