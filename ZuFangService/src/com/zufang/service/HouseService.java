package com.zufang.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.HouseDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.HouseDTO;
import com.zufang.dto.HousePicDTO;
import com.zufang.dto.HouseSearchOptions;
import com.zufang.dto.HouseSearchResult;

public class HouseService {
	
	private HouseDAO dao=new HouseDAO();
	
	public HouseDTO getById(long id) {
		return dao.getById(id);
	}

	public long getTotalCount(long cityId, long typeId) {
		return dao.getTotalCount(cityId, typeId);
	}

	public HouseDTO[] getAll() {
		return dao.getAll();
	}

	public HouseDTO[] getPagedData(long cityId, long typeId, int pageSize, long currentIndex) {
		return dao.getPagedData(cityId, typeId, pageSize, currentIndex);
	}

	public long addnew(HouseDTO house) {
		return dao.addnew(house);
	}

	public void update(HouseDTO house) {
		dao.update(house);
	}

	public void markDeleted(long id) {
		dao.markDeleted(id);
	}

	public HousePicDTO[] getPics(long houseId) {
		return dao.getPics(houseId);
	}

	public long addnewHousePic(HousePicDTO housePic) {
		return dao.addnewHousePic(housePic);
	}

	public void deleteHousePic(long housePicId) {
		dao.deleteHousePic(housePicId);
	}
	
	public HouseSearchResult search(HouseSearchOptions options){
		return dao.search(options);
	}
}
