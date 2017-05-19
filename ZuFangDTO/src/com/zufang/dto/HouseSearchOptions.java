package com.zufang.dto;

public class HouseSearchOptions {

	private long cityId;//城市ID
	private Long typeId;//房源类型 可空
	private Long regionId;//区域ID 可空
	private Integer startMonthRent;//起始月租 可空
	private Integer endMonthRent;//结束房租 可空
	private OrderByType orderByType=OrderByType.MonthRent;//排序方式
	private String keywords;//搜索关键字 可控
	private long pageSize;//每页数据条数
	private long pageIndex;//页码
		
	public enum OrderByType{
		MonthRent,
		Area
	}


	public long getCityId() {
		return cityId;
	}



	public void setCityId(long cityId) {
		this.cityId = cityId;
	}



	public Long getTypeId() {
		return typeId;
	}



	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}



	public Long getRegionId() {
		return regionId;
	}



	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}



	public Integer getStartMonthRent() {
		return startMonthRent;
	}



	public void setStartMonthRent(Integer startMonthRent) {
		this.startMonthRent = startMonthRent;
	}



	public Integer getEndMonthRent() {
		return endMonthRent;
	}



	public void setEndMonthRent(Integer endMonthRent) {
		this.endMonthRent = endMonthRent;
	}



	public OrderByType getOrderByType() {
		return orderByType;
	}



	public void setOrderByType(OrderByType orderByType) {
		this.orderByType = orderByType;
	}



	public String getKeywords() {
		return keywords;
	}



	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}



	public long getPageSize() {
		return pageSize;
	}



	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}



	public long getPageIndex() {
		return pageIndex;
	}



	public void setPageIndex(long pageIndex) {
		this.pageIndex = pageIndex;
	};
	
}
