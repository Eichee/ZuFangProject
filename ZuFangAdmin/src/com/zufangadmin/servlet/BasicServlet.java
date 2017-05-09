package com.zufangadmin.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zufang.service.AdminUserService;
import com.zufang.tools.AjaxResult;
import com.zufangadmin.utils.AdminUtils;


public class BasicServlet extends HttpServlet {
	private static final Logger logger=LogManager.getLogger(BasicServlet.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String action=req.getParameter("action");
		if (StringUtils.isEmpty(action)) {
			AdminUtils.showError(req, resp, "action error");
			logger.debug("action error");
			return;
		}
		
		Class cls=this.getClass();
		
		try {
			Method methodAction=cls.getMethod(action, HttpServletRequest.class,HttpServletResponse.class);
			
			AllowAnonymous allowAnonymous=methodAction.getAnnotation(AllowAnonymous.class);
			if (allowAnonymous==null) {	//need to check login status
				Long adminUserId=AdminUtils.getAdminUserId(req);
				if (adminUserId==null) {
					String ctxPath=req.getContextPath();					
					AdminUtils.showError(req, resp, "未登陆<a target='_top' href='"+ctxPath+"/Index?action=login'>点此登录</a>");
					return;
				}
				
				HasPermission hasPermission=methodAction.getAnnotation(HasPermission.class);
				if (hasPermission!=null) {
					AdminUserService adminUserService=new AdminUserService();
					boolean isOk=adminUserService.hasPermission(adminUserId, hasPermission.value());
					if (!isOk) {
						AdminUtils.showError(req, resp, "无权访问");
						return;
					}
				}
			}		
						
			methodAction.invoke(this, req,resp);
		} catch (NoSuchMethodException|SecurityException e) {
			AdminUtils.showError(req, resp, "查找action出错");
			logger.debug("查找action出错");
			logger.debug(e);
		} catch (Exception e) {
			e.printStackTrace();
			AdminUtils.showError(req, resp, "执行action出错");
			logger.debug("执行action出错");
			logger.debug(e);
		} 
	}

	protected void writeJson(HttpServletResponse resp,AjaxResult ajaxResult) throws IOException {
		resp.setContentType("application/json");
		resp.getWriter().print(ajaxResult.toJson());
	}
}
