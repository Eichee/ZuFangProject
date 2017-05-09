package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.AttachmentDTO;

public class AttachmentDAO {
	

	/**
	 * 查询所有设施
	 * @return
	 */
	public AttachmentDTO[] getAll(){
		ResultSet rs=null;
		List<AttachmentDTO> list=new ArrayList<AttachmentDTO>();
		try{
			rs=JdbcUtils.executeQuery("select * from T_Attachments ");
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new AttachmentDTO[list.size()]);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
	
	/**
	 * 获取房子HoseId有用的设施
	 * @param houseId
	 * @return
	 */
	public AttachmentDTO[] getAttachments(long houseId){
		ResultSet rs=null;
		List<AttachmentDTO> list=new ArrayList<AttachmentDTO>();
		try{
			rs=JdbcUtils.executeQuery("select * from T_Attachments where id in (select AttachmentId from T_HouseAttachments where HouseID=?)",houseId);
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new AttachmentDTO[list.size()]);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}

	private AttachmentDTO toDTO(ResultSet rs) throws SQLException {
		AttachmentDTO dto=new AttachmentDTO();
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setIconName(rs.getString("IconName"));
		dto.setId(rs.getInt("Id"));
		dto.setName(rs.getString("Name"));
		return dto;
	}
}
