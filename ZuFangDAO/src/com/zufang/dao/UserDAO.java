package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.UserDTO;
import com.zufang.tools.CaptchaUtils;
import com.zufang.tools.CommonUtils;

public class UserDAO {
	
	public long addnew(String phoneNum,String password){
		String salt=CaptchaUtils.generateVerifyCode(6, "abcdefg1234567890~!@#$%^&*()");
		String passwordMD5=CommonUtils.calcMD5(password+salt);
		try{
			return JdbcUtils.executeInsert("insert into T_Users (PhoneNum,PasswordHash,PasswordSalt,CreateDateTime,LoginErrorTimes) values (?,?,?,now(),0)", 
					phoneNum,passwordMD5,salt);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public UserDTO getById(long id){
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Users where id=?", id);
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
	
	public UserDTO getByPhoneNum(String phoneNum){
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Users where PhoneNum=?", phoneNum);
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
	
	public boolean checkLogin(String phoneNum,String password){
		UserDTO user=getByPhoneNum(phoneNum);
		if (user==null) {
			return false;
		}
		String salt=user.getPasswordSalt();
		String passwordMD5=CommonUtils.calcMD5(password+salt);
		String userPassword=user.getPasswordHash();
		return userPassword.equals(passwordMD5);
	}
	
	public void updatePassword(long userId,String newPassword){
		UserDTO user=getById(userId);
		if (user==null) {
			throw new IllegalArgumentException("User can not be found by your given userid:"+userId);
		}
		String salt=user.getPasswordSalt();
		String passwordMD5=CommonUtils.calcMD5(newPassword+salt);
		try{
			JdbcUtils.executeNonQuery("update T_Users set PasswordHash=? where id=?", passwordMD5,userId);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public void setUserCityId(long userId, long cityId){
		try{
			JdbcUtils.executeNonQuery("update T_Users set CityId=? where id=?", cityId,userId);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	

	private UserDTO toDTO(ResultSet rs) throws SQLException {
		UserDTO user=new UserDTO();
		user.setCityId(rs.getLong("CItyId"));
		user.setCreateDateTime(rs.getDate("CreateDateTime"));
		user.setId(rs.getLong("Id"));
		user.setLastLoginErrorDateTime(rs.getDate("LastLoginErrorDateTime"));
		user.setPasswordHash(rs.getString("PasswordHash"));
		user.setPasswordSalt(rs.getString("PasswordSalt"));
		user.setPhoneNum(rs.getString("PhoneNum"));
		return user;
	}
}
