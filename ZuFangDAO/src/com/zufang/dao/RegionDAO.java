package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.RegionDTO;

public class RegionDAO {
	
	public RegionDTO getById(long id){
		ResultSet rs=null;
		try {
			rs=JdbcUtils.executeQuery("select * from T_Regions where id=?", id);
			if (rs.next()) {
				return toDTO(rs);
			}
			else{
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

	private RegionDTO toDTO(ResultSet rs) throws SQLException {
		RegionDTO dto=new RegionDTO();
		dto.setCityId(rs.getLong("CityId"));
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setId(rs.getInt("Id"));
		dto.setName(rs.getString("Name"));
		return dto;
	}
	
	public RegionDTO[] getAll(){
		ResultSet rs=null;
		List<RegionDTO> list=new ArrayList<RegionDTO>();
		try {
			rs=JdbcUtils.executeQuery("select * from T_Regions");
			while(rs.next())
			{
				list.add(toDTO(rs));
			}
			return list.toArray(new RegionDTO[list.size()]);
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
		
	}
}
