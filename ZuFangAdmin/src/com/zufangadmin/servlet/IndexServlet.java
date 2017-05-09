package com.zufangadmin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.zufang.dto.AdminUserDTO;
import com.zufang.service.AdminUserService;
import com.zufang.tools.AjaxResult;
import com.zufang.tools.CaptchaUtils;
import com.zufangadmin.utils.AdminUtils;

@WebServlet("/Index")
public class IndexServlet extends BasicServlet {
	
	public void index(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}
	
	@AllowAnonymous
	public void login(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
	}
	
	@AllowAnonymous
	public void loginSubmit(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		String phoneNum=req.getParameter("phoneNum");
		String password=req.getParameter("password");
		String captcha=req.getParameter("captcha");
		String online=req.getParameter("online");
		if (StringUtils.isEmpty(phoneNum)) {
			writeJson(resp, new AjaxResult("error","phone number is needed"));
			return;
		}
		if (StringUtils.isEmpty(password)) {
			writeJson(resp, new AjaxResult("error","password is needed"));
			return;
		}
		if (StringUtils.isEmpty(captcha)) {
			writeJson(resp, new AjaxResult("error", "captcha is needed"));
			return;
		}
		captcha=captcha.toLowerCase();
		String captchaInSession=CaptchaUtils.getCaptchaInSession(req.getSession()).toLowerCase();
		if (!captcha.equals(captchaInSession)) {
			writeJson(resp, new AjaxResult("error", "captcha error"));
			return;
		}
		
		AdminUserService adminUserService=new AdminUserService();
		
		
		if (adminUserService.checkLogin(phoneNum, password)) {
			AdminUserDTO user=adminUserService.getByPhoneNum(phoneNum);
			AdminUtils.setAdminUserId(req, user.getId());
			AdminUtils.setAdminUserCityId(req, user.getCityId());
			writeJson(resp, new AjaxResult("ok"));
		}
		else {
			writeJson(resp, new AjaxResult("error","phoneNum or password error"));
		}
		
	}
	
	@AllowAnonymous
	public void captcha(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String captcha=CaptchaUtils.generateVerifyCode(4);
		CaptchaUtils.setCaptchaInSession(req.getSession(), captcha);
		resp.setContentType("image/jpg");
		CaptchaUtils.outputImage(100, 40, resp.getOutputStream(), captcha);
	}
}
