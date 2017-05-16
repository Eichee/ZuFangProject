package com.zufang.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zufang.dao.HouseAppointmentDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.HouseAppointmentDTO;

public class HouseAppointmentService {

	private HouseAppointmentDAO dao=new HouseAppointmentDAO();
	

	/**
	 * 返回所有预约订单的数量
	 * @return
	 */
	public long getTotalCount(){
		return dao.getTotalCount();
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
		return dao.getPagedData(cityId, status, pageSize, currentIndex);
	}
	
	
	/**
	 * 通过ID获取预约
	 * @param id
	 * @return
	 */
	public HouseAppointmentDTO getById(long id){
		return dao.getById(id);		
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
		return dao.addnew(userId, name, phoneNum, houseId, visitDate);
	}
	
	/**
	 * 抢单
	 * @param adminUserId
	 * @param houseAppointmentId
	 * @return
	 */
	public boolean follow(long adminUserId,long houseAppointmentId){
		return dao.follow(adminUserId, houseAppointmentId);
	}
	
}
