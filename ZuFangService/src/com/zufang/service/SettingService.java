package com.zufang.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.SettingDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.SettingDTO;

public class SettingService {

	private SettingDAO dao=new SettingDAO();
	/**
	 * 设置配置项name的值为value,当表中已存在name项，则更新value；若没有name项，则新增
	 * @param name
	 * @param value
	 */
	public void setValue(String name,String value){
		dao.setValue(name, value);
	}
	
	/**
	 * 获取配置项name的值
	 * @param name
	 * @return
	 */
	public String getValue(String name){
		return getValue(name, null);
	}
	
	public String getValue(String name,String defaultValue){
		return dao.getValue(name, defaultValue);
	}
	
	/**
	 * 查询所有配置项
	 * @return
	 */
	public SettingDTO[] getAll(){
		return dao.getAll();
	}
	
}
