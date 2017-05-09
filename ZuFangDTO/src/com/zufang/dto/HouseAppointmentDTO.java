package com.zufang.dto;

import java.util.Date;

public class HouseAppointmentDTO {
	private long id;
	private long userId;
	private String name;
	private String phoneNum;
	private Date visitDate;
	private long houseId;
	private Date createDateTime;
	private String status;
	private Long followAdminUserId;
	private Date followDateTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public long getHouseId() {
		return houseId;
	}
	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getFollowAdminUserId() {
		return followAdminUserId;
	}
	public void setFollowAdminUserId(Long followAdminUserId) {
		this.followAdminUserId = followAdminUserId;
	}
	public Date getFollowDateTime() {
		return followDateTime;
	}
	public void setFollowDateTime(Date followDateTime) {
		this.followDateTime = followDateTime;
	}
}
