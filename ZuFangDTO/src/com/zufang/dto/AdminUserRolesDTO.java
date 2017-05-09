package com.zufang.dto;

public class AdminUserRolesDTO {
	private long id;
	private long adminUserId;
	private String adminUserName;
	private String adminUserPhoneNum;
	private long[] roleIds;
	private String[] roleNames;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(long adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getAdminUserName() {
		return adminUserName;
	}
	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}
	public String getAdminUserPhoneNum() {
		return adminUserPhoneNum;
	}
	public void setAdminUserPhoneNum(String adminUserPhoneNum) {
		this.adminUserPhoneNum = adminUserPhoneNum;
	}
	public long[] getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(long[] roleIds) {
		this.roleIds = roleIds;
	}
	public String[] getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String[] roleNames) {
		this.roleNames = roleNames;
	}
}
