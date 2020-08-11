package com.fizay.service;

import java.util.List;

import com.fizay.vo.User;



public interface UserService {
	
	List<User> findUserInfo(int id);

	List<String> findUserPermissions(int id);

	String findResourcePermission(String path);

	User findUserInfoByUsername(String username);
}
