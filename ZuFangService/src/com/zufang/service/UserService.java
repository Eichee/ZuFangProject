package com.zufang.service;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.zufang.dao.UserDAO;
import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.UserDTO;
import com.zufang.tools.CaptchaUtils;
import com.zufang.tools.CommonUtils;

public class UserService {
	private UserDAO dao=new UserDAO();
	
	public long addnew(String phoneNum,String password){
		return dao.addnew(phoneNum, password);
	}
	
	public UserDTO getById(long id){
		return dao.getById(id);
	}
	
	public UserDTO getByPhoneNum(String phoneNum){
		return dao.getByPhoneNum(phoneNum);
	}
	
	public boolean checkLogin(String phoneNum,String password){
		return dao.checkLogin(phoneNum, password);
	}
	
	public void updatePassword(long userId,String newPassword){
		dao.updatePassword(userId, newPassword);
	}
	
	public void setUserCityId(long userId, long cityId){
		dao.setUserCityId(userId, cityId);
	}
}
