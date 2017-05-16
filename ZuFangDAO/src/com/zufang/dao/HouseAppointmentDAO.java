package com.zufang.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.HouseAppointmentDTO;

public class HouseAppointmentDAO {

	/**
	 * 返回所有预约订单的数量
	 * @return
	 */
	public long getTotalCount(){
		StringBuilder sb=new StringBuilder();
		sb.append(" select count(1) from t_houseappointments app");
		Number number;
		try{
			number=(Number)JdbcUtils.executeQuery(sb.toString());			
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
		return number.longValue();
	}
	
	
	
	/**
	 * 分页查询
	 * @param cityId
	 * @param status
	 * @param pageSize
	 * @param currentIndex 页码 从1开始
	 * @return
	 */
	public HouseAppointmentDTO[] getPagedData(long cityId,String status,int pageSize,long currentIndex){
		StringBuilder sb = new StringBuilder();
		sb.append(
				"select app.*,u.Name followUserName,reg.Name regionName,com.Name communityName from t_houseappointments app\n");
		sb.append("left join T_AdminUsers u on app.FollowAdminUserId=u.Id\n");
		sb.append("left join T_Houses h on app.HouseId=h.Id\n");
		sb.append("left join T_Regions reg on h.RegionId=reg.Id\n");
		sb.append("left join T_Communities com on h.CommunityId=com.Id\n");
		sb.append("where reg.CityId=? and app.Status=?\n");
		sb.append("limit ?,?");
		ResultSet rs=null;
		List<HouseAppointmentDTO> list=new ArrayList<>();
		try{
			rs=JdbcUtils.executeQuery(sb.toString(), cityId,status,(currentIndex-1)*pageSize,pageSize);
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new HouseAppointmentDTO[list.size()]);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
	
	
	/**
	 * 通过ID获取预约
	 * @param id
	 * @return
	 */
	public HouseAppointmentDTO getById(long id){
		ResultSet rs=null;
		try {
			rs=JdbcUtils.executeQuery("select * from T_HosueAppointments where id=?", id);
			if (rs.next()) {
				return toDTO(rs);
			}
			else{
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}finally{
			JdbcUtils.closeAll(rs);
		}
		
	}
	
	private HouseAppointmentDTO toDTO(ResultSet rs) throws SQLException {
		HouseAppointmentDTO dto=new HouseAppointmentDTO();
		dto.setCreateDateTime(rs.getDate("createDateTime"));
		dto.setFollowAdminUserId(rs.getLong("FollowAdminUserId"));
		dto.setFollowDateTime(rs.getDate("FollowDateTime"));
		dto.setHouseId(rs.getLong("HouseId"));
		dto.setId(rs.getLong("Id"));
		dto.setName(rs.getString("Name"));
		dto.setPhoneNum(rs.getString("PhoneNum"));
		dto.setStatus(rs.getString("Status"));
		dto.setUserId(rs.getLong("UserId"));
		dto.setVisitDate(rs.getDate("VisitDate"));
		return dto;
	}

	/**
	 * 新增一个预约
	 * @param userId 用户Id  可以为null  非会员也可以预约
	 * @param name 姓名
	 * @param phoneNum 手机号
	 * @param houseId 房间Id
	 * @param visitDate 预约看房时间
	 * @return
	 */
	public long addnew(Long userId,String name,String phoneNum,long houseId, Date visitDate){
		try {
			Long id=JdbcUtils.executeInsert("insert into T_HouseAppointments (userId,name,phoneNum,houseId,visitDate) values (?,?,?,?,?)", 
					userId,name,phoneNum,houseId,visitDate);
			return id;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 抢单
	 * @param adminUserId
	 * @param houseAppointmentId
	 * @return
	 */
	public boolean follow(long adminUserId,long houseAppointmentId){
		Connection conn=null;
		try{
			conn=JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			Number number=(Number)JdbcUtils.querySingle(conn,
					"select count(*) from T_HouseAppointments where Id=? and status='新建' for update", houseAppointmentId);
			if (number.intValue()<=0) {
				conn.rollback(); //如果没有查询到的时候一定要rollback， 解锁for update
				return false;
			}
			JdbcUtils.executeNonQuery(conn,
					"update T_HouseAppointments set status='已跟进',FollowAdminUserId=? where id=?", 
					adminUserId, houseAppointmentId);
			conn.commit();
			return true;
		}
		catch(SQLException ex){
			try {
				conn.rollback();
			} catch (SQLException e) {
				
			}
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeQuietly(conn);
		}
	}
}
