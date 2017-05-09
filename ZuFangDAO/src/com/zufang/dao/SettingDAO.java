package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.SettingDTO;

public class SettingDAO {
	
	/**
	 * 设置配置项name的值为value,当表中已存在name项，则更新value；若没有name项，则新增
	 * @param name
	 * @param value
	 */
	public void setValue(String name,String value){
		String oldValue=getValue(name, null);
		try {
			if (oldValue==null) {
				JdbcUtils.executeNonQuery("insert into T_Settings (name,value) values (?,?)", name,value);
			}
			else{
				JdbcUtils.executeNonQuery("update T_Settings set value=? where name=?", value,name);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
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
		try {
			String value=(String)JdbcUtils.querySingle("select value from T_Settings where name=?", name);
			if (value==null) {
				return defaultValue;
			}
			else{
				return value;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 查询所有配置项
	 * @return
	 */
	public SettingDTO[] getAll(){
		List<SettingDTO> list=new ArrayList<SettingDTO>();
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Settings");
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new  SettingDTO[list.size()]);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}

	private SettingDTO toDTO(ResultSet rs) throws SQLException {
		SettingDTO settingDTO=new SettingDTO();
		settingDTO.setId(rs.getInt("Id"));
		settingDTO.setName(rs.getString("Name"));
		settingDTO.setValue(rs.getString("value"));
		return settingDTO;
	}
	
}
