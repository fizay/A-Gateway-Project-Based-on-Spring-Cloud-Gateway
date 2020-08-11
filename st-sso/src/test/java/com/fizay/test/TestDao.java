package com.fizay.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fizay.dao.UserDao;

/**
 * UserDao的测试类，测试各方法是否能准确从数据库查询到值
 * 
 * @author FUZIYAN
 *
 */
@SpringBootTest
public class TestDao {
	
	@Autowired(required = true)
	private UserDao userDao;
	
	
	@Test
	public void testFindUserInfo() {
		System.out.println(userDao.findUserInfo(1));
	}
	
	@Test
	public void testFindPermissionsIdsByRolesIds() {
		Integer[] temp = {1,2};
		System.out.println(userDao.findPermissionsIdsByRolesIds(temp));
	}
	
	@Test
	public void testFindPermissionsByPermissionsIds() {
		Integer[] temp = {1,2,3,4,5};
		System.out.println(userDao.findPermissionsByPermissionsIds(temp));
	}
	
	@Test
	public void testFindResourceIdbyPath() {
		String path = "/getInfo";
		System.out.println(userDao.findResourceIdbyPath(path));
	}
	
	@Test
	public void testFindPermissionIdByResourceId() {
		Integer resourceId = 1;
		System.out.println(userDao.findPermissionIdByResourceId(resourceId));
	}
	
	@Test
	public void testFindPermissionByPermissionId() {
		Integer permissionId = 5;
		System.out.println(userDao.findPermissionByPermissionId(permissionId));
	}
	
	@Test
	public void testFindUserInfoByUsername() {
		System.out.println(userDao.findUserInfoByUsername("FUZIYAN"));
	}
}
