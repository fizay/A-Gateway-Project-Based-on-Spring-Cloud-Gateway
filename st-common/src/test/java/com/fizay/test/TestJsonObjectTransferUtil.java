package com.fizay.test;

import org.junit.jupiter.api.Test;

import com.fizay.util.JsonObjectTransferUtil;

/**
 * 测试JsonObjectTransferUtil是否正常转换json和对象
 * @author FUZIYAN
 *
 */
public class TestJsonObjectTransferUtil {
	
	@Test
	public void testJsonObjectTransferUti() {
		ToolClass objectA = new ToolClass();
		objectA.setId(1);
		objectA.setName("fzy");
		System.out.println("待转换的对象为~" + objectA);
		
		//对象转json
		String jsonA = JsonObjectTransferUtil.toJSON(objectA);
		System.out.println("对象转json~" + jsonA);
		
		//json转对象
		ToolClass objectB = JsonObjectTransferUtil.toObject(jsonA, ToolClass.class);
		System.out.println("json转对象~" + objectB.getId() + objectB.getName());
		
	}
}

/*
 * 测试该类的对象和json之间的转换
 */
class ToolClass{
	private int id;
	private String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ToolClass [id=" + id + ", name=" + name + "]";
	}
}