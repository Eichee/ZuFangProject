package com.zufangadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zufang.dto.PermissionDTO;
import com.zufang.dto.RoleDTO;
import com.zufang.service.PermissionService;
import com.zufang.service.RoleService;
import com.zufang.tools.AjaxResult;
import com.zufang.tools.CommonUtils;
import com.zufangadmin.utils.AdminUtils;

@WebServlet("/role")
public class RoleServlet extends BasicServlet {
	
	private static final Logger logger=LogManager.getLogger(RoleServlet.class);

	@HasPermission("Role.Query")
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			RoleService roleService = new RoleService();
			RoleDTO[] roles = roleService.getAll();
			request.setAttribute("roles", roles);
			request.getRequestDispatcher("/WEB-INF/role/roleList.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			AdminUtils.showError(request, response, "Service Error");
			logger.debug(e);
		}
	}
	
	@HasPermission("Role.Delete")
	public void delete(HttpServletRequest request,HttpServletResponse response) throws IOException{
		long id=Long.parseLong(request.getParameter("id"));
		RoleService roleService=new RoleService();
		roleService.markDeleted(id);
		this.writeJson( response, new AjaxResult("ok"));
		
	}
	
	@HasPermission("Role.AddNew")
	public void add(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		PermissionService permissionService=new PermissionService();
		PermissionDTO[] permissions=permissionService.getAll();
		request.setAttribute("permissions", permissions);
		request.getRequestDispatcher("/WEB-INF/role/roleAdd.jsp").forward(request, response);
	}
	
	@HasPermission("Role.AddNew")
	public void addSubmit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String roleName=request.getParameter("roleName");
		String[] permIds=request.getParameterValues("permId");
		RoleService roleService=new RoleService();
		roleService.addnew(roleName,CommonUtils.toLongArray(permIds) );
		writeJson(response, new AjaxResult("ok"));
		
	}
}
