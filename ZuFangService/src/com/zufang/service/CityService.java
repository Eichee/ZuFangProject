package com.zufang.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.CityDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.CityDTO;

public class CityService {

	private CityDAO dao=new CityDAO();
	
	public CityDTO getById(long id){
		return dao.getById(id);
	}
	
	public CityDTO[] getAll(){
		return dao.getAll();
	}

	
	
}
