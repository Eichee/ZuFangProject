package com.zufang.front.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zufang.service.CityService;
import com.zufang.service.UserService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class FrontUtils {
	public static void showError(HttpServletRequest req,HttpServletResponse resp,String errorMsg) throws ServletException, IOException{
		resp.setStatus(500);
		req.setAttribute("errorMsg", errorMsg);
		req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
	}
	
	
	private static final JedisPool jedisPool=new JedisPool("127.0.0.1");
	public static Jedis createJedis(){
		return jedisPool.getResource();
	}
	
	
	public static void setCurrentUserId(HttpServletRequest req,Long userId){
		req.getSession().setAttribute("UserId", userId);
	}
	
	public static Long getCurrentUserId(HttpServletRequest req){
		return (Long)req.getSession().getAttribute("UserId");
	}
	
	public static long getCurrentCityId(HttpServletRequest req){
		Long userId=getCurrentUserId(req);
		Long cityId;
		//返回当前登录用户的cityid
		if (userId!=null) {
			cityId=new UserService().getById(userId).getCityId();
			if (cityId!=null) {
				return cityId;
			}
		}
		//用户没有登录或者用户没有设置cityid，从session中取
		cityId=(Long)req.getSession().getAttribute("CurrentCityId");
		if (cityId!=null) {
			return cityId;
		}
		//如果都没有 返回数据库中第一个城市
		return new CityService().getAll()[0].getId();
		
	}
}
