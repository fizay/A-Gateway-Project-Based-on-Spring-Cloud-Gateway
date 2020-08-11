package com.fizay.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.fizay.util.ReadTomcatLogUtil;

public class TestReadTomcatLogUtil {

	@Test
	public void testReadTomcatLogutil(){
		String result = ReadTomcatLogUtil.readWantedText("C:/Java/SummerTraining/st-provider01/access_log.2020-08-08.log", "500");
		System.out.println(result);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(format.format(date));
	}
}
