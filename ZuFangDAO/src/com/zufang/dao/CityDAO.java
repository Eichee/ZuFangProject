package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.CityDTO;

public class CityDAO {

	public CityDTO getById(long id){
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Cities where id=?", id);
			if (rs.next()) {
				return toDTO(rs);
			}
			else{
				return null;
			}
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
	
	public CityDTO[] getAll(){
		ResultSet rs=null;
		List<CityDTO> list=new ArrayList<CityDTO>();
		try{
			rs=JdbcUtils.executeQuery("select * from T_Cities");
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new CityDTO[list.size()]);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}

	private CityDTO toDTO(ResultSet rs) throws SQLException {
		CityDTO dto=new CityDTO();
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setId(rs.getInt("Id"));
		dto.setName(rs.getString("Name"));
		return dto;
	}
}
