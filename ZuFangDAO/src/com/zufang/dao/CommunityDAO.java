package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.CommunityDTO;

public class CommunityDAO {
	
	public CommunityDTO[] getByRegionId(long regionId){
		ResultSet rs=null;
		List<CommunityDTO> list=new ArrayList<CommunityDTO>();
		try{
			rs=JdbcUtils.executeQuery("select * from T_Communities where regionId=?", regionId);
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new CommunityDTO[list.size()]);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}

	private CommunityDTO toDTO(ResultSet rs) throws SQLException {
		CommunityDTO dto=new CommunityDTO();
		dto.setBuiltYear(rs.getInt("BuiltYear"));
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setId(rs.getInt(rs.getInt("Id")));
		dto.setLocation(rs.getString("Location"));
		dto.setMediumtext(rs.getString("Mediumtext"));
		dto.setName(rs.getString("Name"));
		dto.setRegionId(rs.getInt("RegionId"));
		return dto;
	}
	
	
}
