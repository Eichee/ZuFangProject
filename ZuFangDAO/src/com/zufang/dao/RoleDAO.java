package com.zufang.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zufang.dao.utils.JdbcUtils;
import com.zufang.dto.RoleDTO;

public class RoleDAO {
	
	/**
	 *  add role
	 * @param roleName
	 * @return
	 */
	public long addnew(String roleName){
		try{
			return JdbcUtils.executeInsert("insert into T_Roles (Name,IsDeleted) values(?,0)", roleName);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public long addnew(Connection conn, String roleName){
		try{
			return JdbcUtils.executeInsert(conn,"insert into T_Roles (Name,IsDeleted) values(?,0)", roleName);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	public void addPermIds(Connection conn,long roleId,long[] permIds){
		try{
			for (int i = 0; i < permIds.length; i++) {
				JdbcUtils.executeNonQuery(conn, "insert into T_RolePermissions (RoleId,PermissionId) values (?,?)", roleId,permIds[i]);
			}
		}
		catch(SQLException e){
			throw new RuntimeException(e);
		}
	}
	
	public long addnew(String roleName,long[] permIds){
		Connection conn=null;
		try{
			conn=JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			long roleId=addnew(conn,roleName);
			addPermIds(conn, roleId, permIds);
			conn.commit();
			return roleId;
		}
		catch(SQLException e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
			}
			throw new RuntimeException(e);
		}
		finally{
			JdbcUtils.closeQuietly(conn);
		}
		
		
	}
	
	
	/**
	 * update role
	 * @param roleId
	 * @param roleName
	 */
	public void update(long	roleId,String roleName){
		try{
			JdbcUtils.executeNonQuery("update T_Roles set Name=? where id=?", roleName,roleId);;
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * soft delete
	 * @param roleId
	 */
	public void markDeleted(long roleId){
		try{
			JdbcUtils.executeNonQuery("update T_Roles set IsDeleted=1 where id=?", roleId);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * get RoleDTO by roleId
	 * @param id
	 * @return
	 */
	public RoleDTO getById(long id){
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Roles where id=?", id);
			if (rs.next()) {
				return toDTO(rs);
			}
			else{
				return null;
			}
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}

	/**
	 * get all roles
	 * @return
	 */
	public RoleDTO[] getAll(){
		List<RoleDTO> list=new ArrayList<>();
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Roles");
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new RoleDTO[list.size()]);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
	
	private RoleDTO toDTO(ResultSet rs) throws SQLException {
		RoleDTO dto=new RoleDTO();
		dto.setDeleted(rs.getBoolean("IsDeleted"));
		dto.setId(rs.getInt("Id"));
		dto.setName(rs.getString("Name"));
		return dto;
	}
	
	/**
	 * add roles to adminuser
	 * @param adminUserId
	 * @param roleIds
	 */
	public void addRoleIds(long adminUserId,long[] roleIds){
		Connection conn=null;
		try{
			conn=JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			for (long roleId : roleIds) {
				JdbcUtils.executeNonQuery(conn, "insert into T_AdminUserRoles (AdminUserId,RoleId) values(?,?)",
						adminUserId,roleId);
			}
			conn.commit();
		}
		catch(SQLException ex){
			JdbcUtils.rollback(conn);
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeQuietly(conn);
		}
	}
	
	/**
	 * update roles for adminuser
	 * @param adminUserId
	 * @param roleIds
	 */
	public void updateRoleIds(long adminUserId,long[] roleIds){
		Connection conn=null;
		try{
			conn=JdbcUtils.getConnection();
			conn.setAutoCommit(false);
			JdbcUtils.executeNonQuery(conn, "delete from T_AdminUserRoles where AdminUserId=?",adminUserId);
			for (long roleId : roleIds) {
				JdbcUtils.executeNonQuery(conn, "insert into T_AdminUserRoles (adminUserId,RoleId) values(?,?)", 
						adminUserId,roleId);
			}
			conn.commit();
		}
		catch(SQLException ex){
			JdbcUtils.rollback(conn);
			throw new RuntimeException(ex);
			
		}
		finally{
			JdbcUtils.closeQuietly(conn);
		}
	}
	
	/**
	 * get all the roles for adminuser
	 * @param adminUserId
	 * @return
	 */
	public RoleDTO[] getByAdminUserId(long adminUserId){
		List<RoleDTO> list=new ArrayList<>();
		ResultSet rs=null;
		try{
			rs=JdbcUtils.executeQuery("select * from T_Roles where id in (select RoleId from T_AdminUserRoles where AdminUserId=?)",
					adminUserId);
			while(rs.next()){
				list.add(toDTO(rs));
			}
			return list.toArray(new RoleDTO[list.size()]);
		}
		catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		finally{
			JdbcUtils.closeAll(rs);
		}
	}
}
