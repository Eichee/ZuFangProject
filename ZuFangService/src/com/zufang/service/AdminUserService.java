package com.zufang.service;

import com.zufang.dao.AdminUserDAO;
import com.zufang.dto.AdminUserDTO;




public class AdminUserService {
	
	
	private AdminUserDAO adminUserDAO=new AdminUserDAO();
	
	public long addAdminUser(String name,String phoneNum,String password,String email,Long cityId){
		return adminUserDAO.addAdminUser(name, phoneNum, password, email, cityId);
	}
	
	
	public void updateAdminUser(long id,String name,String password,String email,Long cityId){
		adminUserDAO.updateAdminUser(id, name, password, email, cityId);
	}
	
	
	public AdminUserDTO getById(long id){
		return adminUserDAO.getById(id);
	}
	
	public boolean checkLogin(String phoneNum, String password) {
		return adminUserDAO.checkLogin(phoneNum, password);
	}

	// 软删除
	public void markDeleted(long adminUserId) {
		adminUserDAO.markDeleted(adminUserId);
	}

	/**
	 * 判断用户是否有某个权限，比如hasPermission(3,"AdminUser.AddNew")
	 * 
	 * @param adminUserId
	 * @param permissionName
	 * @return
	 */
	public boolean hasPermission(long adminUserId, String permissionName) {		
		return adminUserDAO.hasPermission(adminUserId, permissionName);
	}
	
	public AdminUserDTO getByPhoneNum(String phoneNum) {
		return adminUserDAO.getByPhoneNum(phoneNum);
	}
	
	public AdminUserDTO[] getAll(long cityId){
		return adminUserDAO.getAll(cityId);
	}
	
	public AdminUserDTO[] getAll(){
		return adminUserDAO.getAll();
	}
	
}
