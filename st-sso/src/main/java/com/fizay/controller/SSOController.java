package com.fizay.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fizay.util.JsonObjectTransferUtil;
import com.fizay.vo.User;
import com.github.xjs.ezprofiler.annotation.Profiler;

/**
 * 对用户的登录信息进行校验
 * 校验通过，向用户返回之前请求的响应结果
 * @author FUZIYAN
 *
 */
@RestController
public class SSOController {

	@Profiler
	@PostMapping("/userVerify")
	public String loginVerify(@RequestParam("username") String username, 
			@RequestParam("password") String password, 
			@RequestParam("preRequestPath") String path) {
		//测试是否可以获取post提交的参数——yes
		//System.out.println("获取登录页面post提交过来的参数："+ username + " " + password + " "+ path);

		//		User userDB = userService.findUserInfoByUsername(username);
		//取出盐值和passowrd加密，然后用数据库中的密码进行比较。User对象需要改一下

		//对用户提交的信息进行验证，验证通过则制作Token
		User user = new User();

		//暂时写死数据
		if("FUZIYAN".equals(username) && "123456".equals(password)) {
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
			String url = "http://localhost:3001/provider-service" + path;
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("CMSOFT_TOKEN", json);
			try {
				CloseableHttpResponse response = client.execute(httpGet);
				return EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else if("ZHANGSAN".equals(username) && "123456".equals(password)){
			user.setDeptId(1);
			user.setEmail("jacob2014@126.com");
			user.setId(2);
			user.setNote("none");
			user.setPhone("18717023757");
			user.setQpsLevel(50);	//网关中会限流50%，限制访问10次
			user.setSalt("none");
			user.setUsername("ZHANGSAN");
			String json = JsonObjectTransferUtil.toJSON(user);
			CloseableHttpClient client = HttpClients.createDefault();
			String url = "http://localhost:3001/provider-service" + path;
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("CMSOFT_TOKEN", json);
			try {
				CloseableHttpResponse response = client.execute(httpGet);
				return EntityUtils.toString(response.getEntity());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}else {
			return "wrong username/password";
		}
	}

}
