package com.fizay.test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.fizay.util.JsonObjectTransferUtil;
import com.fizay.vo.User;

/*
 * 测试使用HttpClient工具加入请求头后能否通过gateway网关
 */
public class TestHttpClient {
	
	@Test
	public void testGet() throws ClientProtocolException, IOException {
		User user = new User();
		user.setDeptId(1);
		user.setEmail("microfzy@outlook.com");
		user.setId(1);
		user.setNote("none");
		user.setPhone("15700756797");
		user.setQpsLevel(100);
		user.setSalt("none");
		user.setUsername("FUZIYAN");
		String json = JsonObjectTransferUtil.toJSON(user);
		CloseableHttpClient client = HttpClients.createDefault();
		String url = "http://localhost:3001/provider-service/authLimit";
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("CMSOFT_TOKEN", json);
		try {
			CloseableHttpResponse response = client.execute(httpGet);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
