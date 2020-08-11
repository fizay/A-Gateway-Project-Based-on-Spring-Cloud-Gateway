package com.fizay.test;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Test;

/**
 * /**
 * 为Spring cloud gateway的before、between、after谓语提供符合格式要求的时间
 * @author FUZIYAN
 * 
 * 2020/7/31
 */
public class TestZoneTime {
	
	@Test
	public void testZontTime() {
		ZonedDateTime now = ZonedDateTime.now();
		System.out.println(now);
	}
}
