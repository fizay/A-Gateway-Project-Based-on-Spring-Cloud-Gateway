package com.fizay.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 用于读取tomcat日志文件的工具类
 * 筛选出其中异常的信息
 * @author FUZIYAN
 * 
 * 2020/8/11
 *
 */
public class ReadTomcatLogUtil {

	/**
	 * 
	 * @param url	待读取文件的路径
	 * @param wanted	匹配含有wanted字符串的记录行
	 * @return	读取到指定内容，则返回字符串；否则返回null
	 */
	public static String readWantedText(String url, String wanted) {
		try {
			FileReader fr = new FileReader(url);
			BufferedReader br = new BufferedReader(fr);
			String temp = "";// 用于临时保存每次读取的内容
			StringBuilder builder = new StringBuilder();
			while (temp != null) {
				temp = br.readLine();
				if (temp != null && temp.contains(wanted)) {
					builder.append(temp).append("\r\n");
				}
			}
			br.close();
			return builder.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
