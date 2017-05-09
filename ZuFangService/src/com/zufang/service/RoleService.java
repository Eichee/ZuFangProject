package com.zufang.service;

import java.sql.Connection;

import com.zufang.dao.RoleDAO;
import com.zufang.dto.RoleDTO;

public class RoleService {
	
	RoleDAO roleDAO=new RoleDAO();
	
	/**
	 *  add role
	 * @param roleName
	 * @return
	 */
	public long addnew(String roleName){
		return roleDAO.addnew(roleName);
	}
	

	public long addnew(Connection conn, String roleName){
		return roleDAO.addnew(conn, roleName);
	}
	
	public void addPermIds(Connection conn,long roleId,long[] permIds){
		roleDAO.addPermIds(conn, roleId, permIds);
	}
	
	public long addnew(String roleName,long[] permIds){
		return roleDAO.addnew(roleName, permIds);
	}
	
	/**
	 * update role
	 * @param roleId
	 * @param roleName
	 */
	public void update(long	roleId,String roleName){
		roleDAO.update(roleId, roleName);
	}
	
	/**
	 * soft delete
	 * @param roleId
	 */
	public void markDeleted(long roleId){
		roleDAO.markDeleted(roleId);
	}
	
	/**
	 * get RoleDTO by roleId
	 * @param id
	 * @return
	 */
	public RoleDTO getById(long id){
		return roleDAO.getById(id);
	}

	/**
	 * get all roles
	 * @return
	 */
	public RoleDTO[] getAll(){
		return roleDAO.getAll();
	}
	
	
	/**
	 * add roles to adminuser
	 * @param adminUserId
	 * @param roleIds
	 */
	public void addRoleIds(long adminUserId,long[] roleIds){
		roleDAO.addRoleIds(adminUserId, roleIds);
	}
	
	/**
	 * update roles for adminuser
	 * @param adminUserId
	 * @param roleIds
	 */
	public void updateRoleIds(long adminUserId,long[] roleIds){
		 roleDAO.updateRoleIds(adminUserId, roleIds);
	}
	
	/**
	 * get all the roles for adminuser
	 * @param adminUserId
	 * @return
	 */
	public RoleDTO[] getByAdminUserId(long adminUserId){
		return roleDAO.getByAdminUserId(adminUserId);
	}
}
