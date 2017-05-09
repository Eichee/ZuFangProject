package com.zufang.service;


import com.zufang.dao.PermissionDAO;
import com.zufang.dto.PermissionDTO;

public class PermissionService {

	PermissionDAO permissionDAO=new PermissionDAO();
	public PermissionDTO getById(long id){
		return permissionDAO.getById(id);		
	}
	
	public PermissionDTO getByName(String name){
		return permissionDAO.getByName(name);		
	}
	
	public PermissionDTO[] getAll(){
		return permissionDAO.getAll();
	}
	
	public PermissionDTO[] getByRoleId(long roleId){
		return permissionDAO.getByRoleId(roleId);
	}
	
	
}
