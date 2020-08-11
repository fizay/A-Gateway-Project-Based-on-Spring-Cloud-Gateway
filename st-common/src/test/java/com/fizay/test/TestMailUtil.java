package com.fizay.test;

import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;

import com.fizay.util.MailUtil;

/**
 * 测试邮件发送功能是否正常
 * @author FUZIYAN
 *
 */
public class TestMailUtil {
	
	@Test
	public void testMailUtil() throws GeneralSecurityException, MessagingException {
		
		MailUtil mail = new MailUtil();
		mail.sendMail("sent from testMailUtil, my spring cloud gateway project!");
	}
}
