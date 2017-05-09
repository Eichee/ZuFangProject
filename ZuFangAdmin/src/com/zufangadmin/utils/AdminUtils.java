package com.zufangadmin.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminUtils {
	
	public static void showError(HttpServletRequest req,HttpServletResponse resp,String errorMsg) throws ServletException, IOException{
		req.setAttribute("errorMsg", errorMsg);
		resp.setStatus(500);
		req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
	}
	
	public static void setAdminUserId(HttpServletRequest req,long adminUserId){
		req.getSession().setAttribute("adminUserId", adminUserId);
	}
	
	public static void setAdminUserCityId(HttpServletRequest req,Long cityId){
		req.getSession().setAttribute("adminUserCityId", cityId);
	}
	
	public static Long getAdminUserId(HttpServletRequest req){
		return (Long)req.getSession().getAttribute("adminUserId");
	}
	
	public static Long getAdminUserCityId(HttpServletRequest req){
		return (Long)req.getSession().getAttribute("adminUserCityId");
	}
}
