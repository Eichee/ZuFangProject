package com.zufangadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.LongSerializationPolicy;
import com.zufang.dao.RoleDAO;
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
			RoleDTO[] roles = roleService.getAllNotDeleted();
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
	
	@HasPermission("Role.Edit")
	public void edit(HttpServletRequest req,HttpServletResponse resp) throws IOException, ServletException{
		long roleId=Long.parseLong(req.getParameter("roleId"));
		RoleService roleService=new RoleService();
		RoleDTO role=roleService.getById(roleId);
		if (role==null) {
			writeJson(resp, new AjaxResult("error", "failed to search the role"));
			return;
		}
		req.setAttribute("role", role);
		
		PermissionService permService=new PermissionService();
		PermissionDTO[] allPerms=permService.getAll();
		req.setAttribute("permissions", allPerms);
		
		PermissionDTO[] rolePerms=permService.getByRoleId(roleId);
		long[] rolePermIds=new long[rolePerms.length];
		for (int i = 0; i < rolePermIds.length; i++) {
			rolePermIds[i]=rolePerms[i].getId();
		}
		req.setAttribute("rolePermIds", rolePermIds);
		
		req.getRequestDispatcher("/WEB-INF/role/roleEdit.jsp").forward(req, resp);
		
	}
	
	@HasPermission("Role.Edit")
	public void editSubmit(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		long roleId=Long.parseLong(req.getParameter("roleId"));
		String roleName=req.getParameter("roleName");
		String[] permIds=req.getParameterValues("permId");	
		
		RoleService roleService=new RoleService();
		roleService.update(roleId, roleName);
		
		PermissionService permService=new PermissionService();
		permService.updatePermIds(roleId, CommonUtils.toLongArray(permIds));
		
		writeJson(resp, new AjaxResult("ok"));
	}
	
	
}
