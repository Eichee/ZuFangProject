package com.zufang.front.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.zufang.dto.UserDTO;
import com.zufang.front.utils.FrontUtils;
import com.zufang.front.utils.RupengSMSAPI;
import com.zufang.service.UserService;
import com.zufang.tools.AjaxResult;
import com.zufang.tools.CaptchaUtils;

@WebServlet("/User")
public class UserServlet extends BasicServlet {

	//显示注册页面
	public void register(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/User/register.jsp").forward(req, resp);
	}
	
	//发送注册手机短信验证码
	public void registerSendSmsCode(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		//检查图形验证码
		String imgCaptcha=req.getParameter("captcha");
		String imgCaptchaInSession=(String)req.getSession().getAttribute("Captcha");
		if (StringUtils.isEmpty(imgCaptcha)||!imgCaptcha.equalsIgnoreCase(imgCaptchaInSession)) {
			writeJson(resp, new AjaxResult("error","Captcha Error"));
			return;
		}
		String phoneNum=req.getParameter("phoneNum");
		UserService userService=new UserService();
		UserDTO user=userService.getByPhoneNum(phoneNum);
		if (user!=null) {
			writeJson(resp, new AjaxResult("error","PhoneNum has been logined up"));
			return;
		}
		
		String smsCode=CaptchaUtils.generateVerifyCode(4, "1234567890");
		System.out.println(smsCode);
		req.getSession().setAttribute("smsCode", smsCode);
		req.getSession().setAttribute("registerPhoneNum", phoneNum);
		RupengSMSAPI.send(smsCode, phoneNum);		
		writeJson(resp, new AjaxResult("ok"));
	}
	
	public void registerSubmit(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String phoneNum=req.getParameter("phoneNum");
		String smsCode=req.getParameter("smsCode");
		String password=req.getParameter("password");
		String smsCodeInSession=(String)req.getSession().getAttribute("smsCode");
		if (StringUtils.isEmpty(smsCode)||!smsCode.equalsIgnoreCase(smsCodeInSession)) {
			writeJson(resp, new AjaxResult("error", "SmsCode error"));
			return;
		}
		if (StringUtils.isEmpty(phoneNum)||StringUtils.isEmpty(password)) {
			writeJson(resp, new AjaxResult("error","PhoneNum & Password can not be empty"));
			return;
		}
		//必须保证再次提交过来的PhoneNum和接受号码的PhoneNum一致
		String phoneNumInSession=(String)req.getSession().getAttribute("registerPhoneNum");
		if (!phoneNum.equalsIgnoreCase(phoneNumInSession)) {
			writeJson(resp, new AjaxResult("error", "PhoneNum incorrect"));
			return;
		}
		
		UserService userService=new UserService();
		UserDTO user=userService.getByPhoneNum(phoneNum);
		if (user!=null) {
			writeJson(resp, new AjaxResult("error","PhoneNum is registered"));
			return;
		}
		userService.addnew(phoneNum, password);
		writeJson(resp, new AjaxResult("ok"));
	}
	
	public void login(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{
		req.getRequestDispatcher("/WEB-INF/User/login.jsp").forward(req, resp);
	}
	
	public void loginSubmit(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		String phoneNum=req.getParameter("phoneNum");
		String password=req.getParameter("password");
		String captcha=req.getParameter("captcha");
		String captchaInSession=req.getParameter("captcha");
		if (StringUtils.isEmpty(captcha)||!captcha.equalsIgnoreCase(captchaInSession)) {
			writeJson(resp, new AjaxResult("error", "Captcha Error"));
			return;
		}
		if (StringUtils.isEmpty(phoneNum)||StringUtils.isEmpty(password)) {
			writeJson(resp, new AjaxResult("error","PhoneNum or Password Empty"));
			return;
		}
		UserService userService=new UserService();
		boolean loginState=userService.checkLogin(phoneNum, password);
		if (loginState) {
			Long userId=userService.getByPhoneNum(phoneNum).getId();
			FrontUtils.setCurrentUserId(req, userId);
			writeJson(resp, new AjaxResult("ok"));
		}
		else{
			writeJson(resp, new AjaxResult("error","Fail to login"));
		}
	}
	
	public void captcha(HttpServletRequest req,HttpServletResponse resp) throws IOException{
		resp.setContentType("image/jpeg");
		String code=CaptchaUtils.generateVerifyCode(4);
		req.getSession().setAttribute("Captcha", code);
		CaptchaUtils.outputImage(100, 50 , resp.getOutputStream(), code);
	}
	
}
