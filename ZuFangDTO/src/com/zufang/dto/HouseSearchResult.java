package com.zufang.dto;

public class HouseSearchResult {

	private HouseDTO[] houses;
	private long totalCount;
	public HouseDTO[] getHouses() {
		return houses;
	}
	public void setHouses(HouseDTO[] houses) {
		this.houses = houses;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
}
