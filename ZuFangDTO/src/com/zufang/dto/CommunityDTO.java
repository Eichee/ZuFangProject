package com.zufang.dto;

public class CommunityDTO {
	private long id;
	private String name;
	private long regionId;
	private boolean isDeleted;
	private String location;
	private String mediumtext;
	private Integer builtYear;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getRegionId() {
		return regionId;
	}
	public void setRegionId(long regionId) {
		this.regionId = regionId;
	}
	public boolean isDeleted() {
		return isDeleted;
	}
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMediumtext() {
		return mediumtext;
	}
	public void setMediumtext(String mediumtext) {
		this.mediumtext = mediumtext;
	}
	public Integer getBuiltYear() {
		return builtYear;
	}
	public void setBuiltYear(Integer builtYear) {
		this.builtYear = builtYear;
	}
}
