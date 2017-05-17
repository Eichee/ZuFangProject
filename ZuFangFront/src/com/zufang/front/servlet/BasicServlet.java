package com.zufang.front.servlet;

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

import com.zufang.front.utils.FrontUtils;
import com.zufang.tools.AjaxResult;

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
			FrontUtils.showError(req, resp, "action error");
			logger.debug("action error:"+action);
			return;
		}
		
		Class cls=this.getClass();
		try{
			Method methodAction=cls.getMethod(action, HttpServletRequest.class,HttpServletResponse.class);
			methodAction.invoke(this, req,resp);
		}
		catch(NoSuchMethodException|SecurityException e){
			FrontUtils.showError(req, resp, "find action error");
			logger.debug(e);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void writeJson(HttpServletResponse resp,AjaxResult ajaxResult) throws IOException {
		resp.setContentType("application/json");
		resp.getWriter().print(ajaxResult.toJson());
	}
	
}
