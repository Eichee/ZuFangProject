package com.zufangadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zufang.dto.AdminUserDTO;
import com.zufang.dto.CityDTO;
import com.zufang.service.AdminUserService;
import com.zufang.service.CityService;
import com.zufang.tools.AjaxResult;

@WebServlet("/adminuser")
public class AdminUserServlet extends BasicServlet{

	@HasPermission("AdminUser.Query")
	public void list(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		AdminUserService adminUserService=new AdminUserService();
		AdminUserDTO[] adminUsers= adminUserService.getAll();
		req.setAttribute("adminusers", adminUsers);
		req.getRequestDispatcher("/WEB-INF/adminUser/adminUserList.jsp").forward(req, resp);
	}
	
	@HasPermission("AdminUser.AddNew")
	public void add(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		//城市查询
		CityService cityService=new CityService();
		CityDTO[] cities=cityService.getAll();
		req.setAttribute("cities", cities);
		
		
		req.getRequestDispatcher("/WEB-INF/adminUser/adminUserAdd.jsp").forward(req, resp);
	}
	
	@HasPermission("AdminUser.AddNew")
	public void addSubmit(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String username=req.getParameter("username");
		String password=req.getParameter("newpassword2");
		String usertel=req.getParameter("usertel");
		String email=req.getParameter("email");
		long cityid=Long.parseLong(req.getParameter("cityid"));
		
		AdminUserService adminUserService=new AdminUserService();
		AdminUserDTO adminUser= adminUserService.getByPhoneNum(usertel);
		if (adminUser!=null) {
			writeJson(resp, new AjaxResult("error","usertel has been logined up"));
			return;
		}
		adminUserService.addAdminUser(username, usertel, usertel, email, cityid);		
		writeJson(resp, new AjaxResult("ok"));
	}
	
	@HasPermission("AdminUser.Edit")
	public void edit(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		CityService cityService=new CityService();
		CityDTO[] cities=cityService.getAll();
		req.setAttribute("cities", cities);
		
		long adminUserId=Long.parseLong(req.getParameter("id"));
		AdminUserService adminUserService=new AdminUserService();
		AdminUserDTO adminUser=adminUserService.getById(adminUserId);
		req.setAttribute("admin", adminUser);
		
		req.getRequestDispatcher("/WEB-INF/adminUser/adminUserEdit.jsp").forward(req, resp);
	}
	
	@HasPermission("AdminUser.Edit")
	public void editSubmit(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		long adminUserId=Long.parseLong(req.getParameter("adminUserId"));
		String username=req.getParameter("username");
		String password=req.getParameter("newpassword2");
		String phoneNum=req.getParameter("usertel");
		String email=req.getParameter("email");
		long cityId=Long.parseLong(req.getParameter("cityid"));
		
		AdminUserService adminUserService=new AdminUserService();
		AdminUserDTO adminUser= adminUserService.getById(adminUserId);
		if (adminUser==null) {
			writeJson(resp, new AjaxResult("error", "failed to find the adminuser"));
			return;
		}
		adminUserService.updateAdminUser(adminUserId, username, password, email, cityId);
		writeJson(resp, new AjaxResult("ok"));
		
	}
	
	@HasPermission("AdminUser.Delete")
	public void delete(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		long id=Long.parseLong(req.getParameter("id"));
		AdminUserService adminUserService=new AdminUserService();
		adminUserService.markDeleted(id);
		
		writeJson(resp, new AjaxResult("ok"));
	}
	
}
