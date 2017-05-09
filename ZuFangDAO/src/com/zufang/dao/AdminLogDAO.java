package com.zufang.dao;

import java.sql.SQLException;

import com.zufang.dao.utils.JdbcUtils;

public class AdminLogDAO {
	
	/**
	 * 插入日志
	 * @param adminUserId
	 * @param message
	 */
	public void addnew (long adminUserId,String message){
		try {
			JdbcUtils.executeNonQuery("insert into T_AdminLogs (AdminUserId,CreateDatetime,Message) values(?,now(),?", adminUserId,message);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
