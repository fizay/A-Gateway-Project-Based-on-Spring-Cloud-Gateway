package com.fizay.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 对象转json和json转对象
 * 使用ObjectMapper类的方法，抛出RuntimeException异常
 * @author FUZIYAN —— 2020/8/3
 *
 */
public class JsonObjectTransferUtil {
	private static final ObjectMapper MAPPER = new ObjectMapper();

	public static String toJSON(Object target) {
		String json = null;
		try {
			json = MAPPER.writeValueAsString(target);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return json;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param json	json串
	 * @param targetClass	目标对象的.class对象
	 * @return
	 */
	public static <T> T toObject(String json,Class<T> targetClass) {
		T target = null;
		try {
			target = MAPPER.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return target;
	}
}
