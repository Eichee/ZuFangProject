package com.zufang.dto;

import java.util.Date;

public class OwnerDelegateDTO {
	private long id;
	private Long userId;
	private String name;
	private String telphoneNum;
	private Date createDateTime;
	private Date rentDateTime;
	private long roomTypeId;
	private String communityName;
	private int typeId;
	private Long followAdminUserId;
	private Date followDateTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelphoneNum() {
		return telphoneNum;
	}
	public void setTelphoneNum(String telphoneNum) {
		this.telphoneNum = telphoneNum;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public Date getRentDateTime() {
		return rentDateTime;
	}
	public void setRentDateTime(Date rentDateTime) {
		this.rentDateTime = rentDateTime;
	}
	public long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
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
