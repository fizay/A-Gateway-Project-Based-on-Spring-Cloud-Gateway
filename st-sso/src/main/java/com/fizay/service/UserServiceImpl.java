package com.fizay.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fizay.dao.UserDao;
import com.fizay.vo.User;

/**
 * 查询用户角色、权限信息
 * 查询资源需要的权限信息
 * @author FUZIYAN
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> findUserInfo(int id) {
		return userDao.findUserInfo(id);
	}

	@Override
	public List<String> findUserPermissions(int id) {
		//通过用户id查询用户-角色表，获取对应的角色id的集合(用户可能具有多个角色)
		List<Integer> rolesIds = userDao.findRolesIdsByUserId(id);
		if(rolesIds == null || rolesIds.size() == 0) {
			throw new RuntimeException("当前用户未获得角色");
		}
		
		Integer[] array = {};	//不加array，rolesIds.toArray()返回Object
		//通过角色id查询角色-权限表，获取对应的权限id的集合(角色可能具有多个权限，需要去重)——不用去重，后期将permissions放入map即可
		List<Integer> permissionsIds = userDao.findPermissionsIdsByRolesIds(rolesIds.toArray(array));
		if(permissionsIds == null || permissionsIds.size() == 0) {
			throw new RuntimeException("该角色未分配权限");
		}
		
		//通过权限id查找权限
		List<String> permissions = userDao.findPermissionsByPermissionsIds(permissionsIds.toArray(array));
		return permissions;
	}

	@Override
	public String findResourcePermission(String path) {
		
		//根据path查询资源表，获得资源id
		Integer resourceId = userDao.findResourceIdbyPath(path);
		if(resourceId == null || resourceId == 0) {
			throw new RuntimeException("没有查到该资源对应的权限");
		}
		
		//根据资源id，查询资源-权限表，获得对应的权限id
		Integer permissionId = userDao.findPermissionIdByResourceId(resourceId);
		if(permissionId == null || permissionId == 0) {
			throw new RuntimeException("没有查到该资源对应的权限");
		}
		
		//根据权限id，查询权限表，获得权限
		String permission = userDao.findPermissionByPermissionId(permissionId);
		return permission;
	}

	@Override
	public User findUserInfoByUsername(String username) {
		return userDao.findUserInfoByUsername(username);
	}

}
