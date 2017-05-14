package com.zufang.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.IdNameDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.IdNameDTO;

public class IdNameService {

	private IdNameDAO dao=new IdNameDAO();
	
	/**
	 * 插入
	 * @param typeName
	 * @param name
	 * @return
	 */
	public long addIdName(String typeName,String name){
		return dao.addIdName(typeName, name);
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public IdNameDTO getById(long id){
		return dao.getById(id);
	}

	
	/**
	 * 查询所有
	 * @return
	 */
	public IdNameDTO[] getAll(String typeName){
		return dao.getAll(typeName);
	}
}
