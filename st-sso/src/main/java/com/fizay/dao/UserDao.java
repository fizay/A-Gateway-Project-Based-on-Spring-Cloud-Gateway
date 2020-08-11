package com.fizay.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fizay.vo.User;

public interface UserDao {
	
	List<User> findUserInfo(int id);
	
	//根据用户id查询角色ids
	List<Integer> findRolesIdsByUserId(int id);

	//根据角色ids查询权限ids
	List<Integer> findPermissionsIdsByRolesIds(@Param("rolesIds")Integer[] rolesIds);

	//根据权限ids获得权限
	List<String> findPermissionsByPermissionsIds(@Param("permissionsIds")Integer[] permissionsIds);

	Integer findResourceIdbyPath(@Param("path")String path);

	Integer findPermissionIdByResourceId(@Param("resourceId")Integer resourceId);

	String findPermissionByPermissionId(@Param("permissionId")Integer permissionId);
	
	//登录验证，根据用户名查询用户信息
	User findUserInfoByUsername(@Param("username")String username);
	
	
}
