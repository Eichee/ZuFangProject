package com.zufang.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.AdminUserDTO;
import com.zufang.tools.CommonUtils;

public class AdminUserDAO {
	
	public long addAdminUser(String name,String phoneNum,String password,String email,Long cityId){
		String passwordHash=CommonUtils.calcMD5(password);
		StringBuilder sb=new StringBuilder();
		sb.append(
				"insert into T_AdminUsers(Name,PhoneNum,PasswordHash,Email,CityId,LoginErrorTimes,LastLoginErrorDateTime,IsDeleted,CreateDateTime)\n");
		sb.append("values(?,?,?,?,?,0,null,0,now())");
		try{
			return JdbcUtils.executeInsert(sb.toString(), name,phoneNum,passwordHash,email,cityId);
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	
	public void updateAdminUser(long id,String name,String password,String email,Long cityId){
		if (StringUtils.isEmpty(password)) {
			StringBuilder sb=new StringBuilder();
			sb.append("update T_AdminUsers set Name=?,Email=?,CityId=?\n");
			sb.append("where id=?");
			try {
				JdbcUtils.executeNonQuery(sb.toString(), name, email, cityId, id);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		else{
			String passwordHash = CommonUtils.calcMD5(password);
			StringBuilder sb = new StringBuilder();
			sb.append("update T_AdminUsers set Name=?,PasswordHash=?,Email=?,CityId=?\n");
			sb.append("where id=?");
			try {
				JdbcUtils.executeNonQuery(sb.toString(), name, passwordHash, email, cityId, id);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	
	public AdminUserDTO getById(long id){
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery(
					"select u.*,c.Name cityName from T_AdminUsers u left join T_Cities c on u.CityId=c.Id where u.Id=? and u.IsDeleted=0",
					id);
			if (rs.next()) {
				return toAdminUserDTO(rs);
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
	
	public boolean checkLogin(String phoneNum, String password) {
		AdminUserDTO user = getByPhoneNum(phoneNum);
		if (user == null)// 手机号错误
		{
			return false;
		}

		String passwordHash = CommonUtils.calcMD5(password);

		if (user.getPasswordHash().equals(passwordHash)) {
			return true;
		} else {
			return false;
		}
	}

	// 软删除
	public void markDeleted(long adminUserId) {
		try {
			JdbcUtils.executeNonQuery("Update T_AdminUsers set IsDeleted=1 where Id=?", adminUserId);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * 判断用户是否有某个权限，比如hasPermission(3,"AdminUser.AddNew")
	 * 
	 * @param adminUserId
	 * @param permissionName
	 * @return
	 */
	public boolean hasPermission(long adminUserId, String permissionName) {		
		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from t_permissions where Id in\n");
		sb.append("(\n");
		sb.append("   select PermissionId from t_rolepermissions where RoleId in\n");
		sb.append("   (\n");
		sb.append("      select RoleId from t_adminuserroles where AdminUserId=?\n");
		sb.append("   )\n");
		sb.append(")\n");
		sb.append("and Name =?\n");
		try {
			Number number = (Number) JdbcUtils.querySingle(sb.toString(), adminUserId, permissionName);			
			return number.intValue()>0;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public AdminUserDTO getByPhoneNum(String phoneNum) {
		ResultSet rs = null;
		try {
			rs = JdbcUtils.executeQuery(
					"select u.*,c.Name cityName from T_AdminUsers u left join T_Cities c on u.CityId=c.Id where u.PhoneNum=? and u.IsDeleted=0",
					phoneNum);
			if (rs.next()) {
				return toAdminUserDTO(rs);
			} else {
				return null;
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeAll(rs);
		}
	}
	
	public AdminUserDTO[] getAll(long cityId){
		StringBuilder sb = new StringBuilder();
		sb.append("select u.*,c.Name cityName from T_AdminUsers u\n");
		sb.append("left join T_cities c on u.CityId=c.Id where u.IsDeleted=0 and u.CityId=?\n");

		List<AdminUserDTO> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = JdbcUtils.executeQuery(sb.toString(), cityId);
			while (rs.next()) {
				list.add(toAdminUserDTO(rs));
			}
			return list.toArray(new AdminUserDTO[list.size()]);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeAll(rs);
		}
	}
	
	public AdminUserDTO[] getAll(){
		StringBuilder sb = new StringBuilder();
		sb.append("select u.*,c.Name cityName from T_AdminUsers u \n");
		sb.append("left join T_cities c on u.CityId=c.Id where u.IsDeleted=0 \n");

		List<AdminUserDTO> list = new ArrayList<>();
		ResultSet rs = null;
		try {
			rs = JdbcUtils.executeQuery(sb.toString());
			while (rs.next()) {
				list.add(toAdminUserDTO(rs));
			}
			return list.toArray(new AdminUserDTO[list.size()]);
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.closeAll(rs);
		}
	}


	private AdminUserDTO toAdminUserDTO(ResultSet rs) throws SQLException {
		AdminUserDTO adminUser = new AdminUserDTO();
		adminUser.setId(rs.getLong("Id"));
		adminUser.setName(rs.getString("Name"));
		adminUser.setCityId((Long) rs.getObject("CityId"));
		adminUser.setCityName(rs.getString("cityName"));
		adminUser.setCreateDateTime(rs.getDate("CreateDateTime"));
		adminUser.setDeleted(rs.getBoolean("IsDeleted"));
		adminUser.setEmail(rs.getString("Email"));
		adminUser.setLastLoginErrorDateTime(rs.getDate("LastLoginErrorDateTime"));
		adminUser.setLoginErrorTimes(rs.getInt("LoginErrorTimes"));
		adminUser.setPasswordHash(rs.getString("PasswordHash"));
		adminUser.setPhoneNum(rs.getString("PhoneNum"));
		return adminUser;
	}
	
	
	
	
}
