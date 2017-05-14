package com.zufang.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.RegionDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.RegionDTO;

public class RegionService {
	private RegionDAO dao=new RegionDAO();
	
	public RegionDTO getById(long id){
		return dao.getById(id);
	}

	public RegionDTO[] getAll(long cityId){
		return dao.getAll(cityId);
	}
	
	
	public RegionDTO[] getAll(){
		return dao.getAll();
	}
}
