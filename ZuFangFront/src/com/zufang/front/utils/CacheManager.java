package com.zufang.front.utils;

import org.apache.commons.lang3.StringUtils;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.zufang.tools.CommonUtils;

public class CacheManager {
	
	private static final CacheManager instance=new CacheManager();
	
	public static CacheManager getManager(){
		return instance;
	}
	
	/**
	 * 在缓存中保存键值对
	 * @param name
	 * @param value
	 * @param liveSeconds
	 */
	public void setValue(String name,Object value,int liveSeconds){
		Gson gson=CommonUtils.createGson();
		String json=gson.toJson(value);
		Jedis jedis=FrontUtils.createJedis();
		try{
			jedis.setex(name, liveSeconds, json);
		}
		finally{
			jedis.close();
		}
		
	}
	
	/**
	 * 从缓存中读取值
	 * @param name
	 * @param clz
	 * @return
	 */
	public Object getValue(String name,Class clz){
		Gson gson=CommonUtils.createGson();
		Jedis jedis=FrontUtils.createJedis();
		try{
			String json=jedis.get(name);
			if (StringUtils.isEmpty(json)) {
				return null;
			}
			else{
				return gson.fromJson(json, clz);
			}
		}
		finally{
			jedis.close();
		}
	}
}
