package com.zufang.front.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
}
