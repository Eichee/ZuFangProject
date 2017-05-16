package com.zufang.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommonUtils {
	
	/**
	 * convert array to list
	 * @param array
	 */
	public static List<Object> toList(Object array){
		if (!array.getClass().isArray()) {
			throw new IllegalArgumentException("parameter is not array");
		}
		List<Object> list=new ArrayList<>();
		int len=Array.getLength(array);
		for (int i = 0; i < len; i++) {
			Object obj=Array.get(array, i);
			list.add(obj);
		}
		return list;
	}
	
	
	public static Gson createGson(){
		return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	}
	
	public final static String calcMD5(String s)
	{
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		try
		{
			byte[] btInput = s.getBytes();
			// 获得MD5摘要算法的 MessageDigest 对象
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 使用指定的字节更新摘要
			mdInst.update(btInput);
			// 获得密文
			byte[] md = mdInst.digest();
			// 把密文转换成十六进制的字符串形式
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++)
			{
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public final static String calcMD5(InputStream inStream)
	{
		MessageDigest digest = null;
		byte buffer[] = new byte[1024];
		int len;
		try
		{
			digest = MessageDigest.getInstance("MD5");
			while ((len = inStream.read(buffer, 0, 1024)) != -1)
			{
				digest.update(buffer, 0, len);
			}
		} catch (NoSuchAlgorithmException | IOException e)
		{
			throw new RuntimeException(e);
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}
	
	public static long[] toLongArray(String[] strs){
		if (strs==null) {
			return new long[0];
		}
		long[] longArray=new long[strs.length];
		for (int i = 0; i < longArray.length; i++) {
			longArray[i]=Long.parseLong(strs[i]);
		}
		return longArray;
	}
	
	public static String urlEncodeUTF8(String url){
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
