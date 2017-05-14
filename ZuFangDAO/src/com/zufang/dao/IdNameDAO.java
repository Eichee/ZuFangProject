package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.IdNameDTO;

public class IdNameDAO {
	
	/**
	 * 插入
	 * @param typeName
	 * @param name
	 * @return
	 */
	public long addIdName(String typeName,String name){
		try {
			return JdbcUtils.executeInsert("insert into T_IdNames (TypeName,Name,IsDeleted) values(?,?,0)", typeName,name);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public IdNameDTO getById(long id){
		ResultSet rs=null;
		try {
			rs=JdbcUtils.executeQuery("select * from T_IdNames where id=?", id);
			if (rs.next()) {
				return toDTO(rs);
			}
			else {
				return null;
			}
		} 
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}

	private IdNameDTO toDTO(ResultSet rs) throws SQLException {
		IdNameDTO dto=new IdNameDTO();
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setId(rs.getInt("Id"));
		dto.setName(rs.getString("Name"));
		dto.setTypeName(rs.getString("TypeName"));
		return dto;
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public IdNameDTO[] getAll(String typeName){
		List<IdNameDTO> list=new ArrayList<IdNameDTO>();
		ResultSet rs=null;
		try {
			rs=JdbcUtils.executeQuery("select * from T_IdNames where typeName=? ",typeName);
			while (rs.next()) {
				list.add(toDTO(rs));				
			}
			return list.toArray(new IdNameDTO[list.size()]);
		} 
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
}
