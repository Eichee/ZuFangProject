package com.zufang.dao.utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;


public class JdbcUtils {
	final static BasicDataSource ds;
	static{
		Properties prop=new Properties();
		try{
			prop.load(JdbcUtils.class.getResourceAsStream("/dbcp2.properties"));
			String driverClassName=prop.getProperty("driverClassName");
			String url=prop.getProperty("url");
			String username=prop.getProperty("username");
			String password=prop.getProperty("password");
			ds=new BasicDataSource();
			ds.setDriverClassName(driverClassName);
			ds.setUrl(url);
			ds.setUsername(username);
			ds.setPassword(password);
		}
		catch(IOException ex){
			throw new RuntimeException(ex);
		}
	}
	
	
	public static void closeQuietly(AutoCloseable clo){
		if (clo!=null) {
			try{
				clo.close();
			}
			catch(Exception ex){
				
			}
		}
	}
	
	public static void closeAll(ResultSet rs){
		if (rs==null) {
			return;
		}
		try{
			Statement st=rs.getStatement();
			Connection conn=st.getConnection();
			closeQuietly(rs);
			closeQuietly(st);
			closeQuietly(conn);
		}
		catch(SQLException ex){
			
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return ds.getConnection();
	}
	
	/**
	 * 执行非查询语句
	 * @param conn
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	public static void executeNonQuery(Connection conn,String sql,Object... params) throws SQLException{
		PreparedStatement ps=null;
		try{
			ps=conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			ps.execute();
		}
		finally{
			closeQuietly(ps);
		}
	}

	public static void executeNonQuery(String sql, Object... params) throws SQLException{
		Connection conn=getConnection();
		try{
			executeNonQuery(conn, sql, params);
		}
		finally{
			closeQuietly(conn);
		}
	}
	
	/**
	 * 执行查询语句
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet executeQuery(Connection conn,String sql,Object... params ) throws SQLException{
		PreparedStatement ps=null;
		try{
			ps=conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			return ps.executeQuery();
		}
		catch(SQLException ex){
			closeQuietly(ps);
			throw ex;
		}
	}
	
	public static ResultSet executeQuery(String sql,Object... params) throws SQLException{
		Connection conn=getConnection();
		try{
			return executeQuery(conn, sql, params);
		}
		catch(SQLException ex){
			closeQuietly(conn);
			throw ex;
		}
	}

	/**
	 * 执行插入语句，返回所插入数据的主键
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException 
	 */
	public static long executeInsert(Connection conn,String sql,Object... params) throws SQLException{
		PreparedStatement psInsert=null;
		PreparedStatement psLastInsertId=null;
		ResultSet rs=null;
		try{
			psInsert=conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				psInsert.setObject(i+1, params[i]);
			}
			psInsert.execute();
			
			psLastInsertId=conn.prepareStatement("select Last_insert_id()");
			rs=psLastInsertId.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}
			else{
				throw new RuntimeException("没有查到自增字段的值");
			}
		}
		finally{
			closeQuietly(psInsert);
			closeQuietly(psLastInsertId);
			closeQuietly(rs);
		}
	}
	
	public static long executeInsert(String sql,Object... params) throws SQLException{
		Connection conn=getConnection();
		try{
			return executeInsert(conn, sql, params);
		}
		finally{
			closeQuietly(conn);
		}
	}
	
	/**
	 * 执行查询语句，返回查询结果第一行第一列的值
	 * @param conn
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public static Object querySingle(Connection conn,String sql,Object... params) throws SQLException{
		PreparedStatement ps=null;
		ResultSet rs=null;
		try{
			ps=conn.prepareStatement(sql);
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i+1, params[i]);
			}
			rs=ps.executeQuery();
			if (rs.next()) {
				return rs.getObject(1);
			}
			else{
				return null;
			}
		}
		finally{
			closeQuietly(ps);
			closeQuietly(rs);
		}
		
	}
	
	public static Object querySingle(String sql, Object... params) throws SQLException{
		Connection conn=getConnection();
		try{
			return querySingle(conn, sql, params);
		}
		finally{
			closeQuietly(conn);
		}
	}

	public static void rollback(Connection conn){
		if (conn!=null) {
			try{
				conn.rollback();
			}
			catch(SQLException ex){
				
			}
		}
	}

}
