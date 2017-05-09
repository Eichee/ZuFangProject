package com.zufang.service;


import java.sql.Connection;

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
	
	public void updatePermIds(long roleId,long[] permIds){
		permissionDAO.updatePermIds(roleId, permIds);
	}
	
	public void addPermIds(Connection conn,long roleId,long[] permIds){
		permissionDAO.addPermIds(conn, roleId, permIds);
	}
	
	public void addPermIds(long roleId,long[] permIds){
		 permissionDAO.addPermIds(roleId, permIds);
	}
	
}
