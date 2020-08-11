package com.fizay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fizay.service.UserService;
import com.fizay.util.JsonObjectTransferUtil;
import com.github.xjs.ezprofiler.annotation.Profiler;

/**
 * 直接访问：http://localhost:8001/findUserInfo?id=1确认是否响应正确结果
 * @author FUZIYAN
 *
 */
@RestController
public class UserController {
	
	    @Autowired
	    private UserService userService;
	    
	    @Profiler
	    @RequestMapping("/findUserInfo")
	    public String findAll(@RequestParam(name = "id") Integer id){
	    	
	        return JsonObjectTransferUtil.toJSON(userService.findUserInfo(id));
	    }
	    
	    @Profiler
	    @RequestMapping("/findUserPermissions")
	    public String findUserPermissions(@RequestParam(name = "id") Integer id) {
	    	
	    	return JsonObjectTransferUtil.toJSON(userService.findUserPermissions(id));
	    }
	    
	    @Profiler
	    @RequestMapping("/findResourcePermission")
	    public String findResourcePermission(@RequestParam(name = "path") String path) {
	    	
	    	return JsonObjectTransferUtil.toJSON(userService.findResourcePermission(path));
	    }
}
