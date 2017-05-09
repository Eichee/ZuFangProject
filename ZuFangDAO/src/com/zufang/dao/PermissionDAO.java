package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.PermissionDTO;

public class PermissionDAO {

	public PermissionDTO getById(long id){
		ResultSet rs=null;
		try {
			rs=JdbcUtils.executeQuery("select * from T_Permissions where id=?", id);
			if (rs.next()) {
				return toDTO(rs);
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JdbcUtils.closeAll(rs);
		}
		
	}
	
	public PermissionDTO getByName(String name){
		ResultSet rs=null;
		try {
			rs=JdbcUtils.executeQuery("select * from T_Permissions where name=?", name);
			if (rs.next()) {
				return toDTO(rs);
			}
			else{
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			JdbcUtils.closeAll(rs);
		}
		
	}
	
	public PermissionDTO[] getAll(){
		List<PermissionDTO> list=new ArrayList<PermissionDTO>();
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Permissions");
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new PermissionDTO[list.size()]);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
	
	public PermissionDTO[] getByRoleId(long roleId){
		List<PermissionDTO> list=new ArrayList<PermissionDTO>();
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Permissions where id in (select PermissionId from T_RolePermissions where RoleId=?)",roleId);
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new PermissionDTO[list.size()]);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
	
	

	private PermissionDTO toDTO(ResultSet rs) throws SQLException {
		PermissionDTO dto=new PermissionDTO();
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setDescription(rs.getString("Description"));
		dto.setId(rs.getInt("Id"));
		dto.setName(rs.getString("Name"));
		return dto;
	}
}
