package com.zufang.tools;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Functions {
	
	/**
	 * 判断value是否存在于values这个集合或者数组中
	 * @param values
	 * @param value
	 * @return
	 */
	public static boolean contains(Object values,Object value){
		if (values==null||value==null) {
			return false;
		}
		Iterable iterable;
		if (values.getClass().isArray()) {
			iterable=CommonUtils.toList(values);
		}
		else if (values instanceof Iterable) {
			iterable=(Iterable)values;
		}
		else{
			throw new IllegalArgumentException("The first parameter values must be array or iterable");
		}
		for (Object object : iterable) {
			if (value.equals(object)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 再queryString后面增加一个键值对
	 * @param queryString
	 * @param key
	 * @param value
	 * @return
	 */
	public static String addQueryStringPart(String queryString,String key,String value){
		LinkedHashMap<String, String> map=new LinkedHashMap<>();
		String[] segments=queryString.split("&");
		for (String segment : segments) {
			String[] strs=segment.split("=");
			String segName=strs[0];
			String segValue=strs[1];
			map.put(segName, segValue);
		}
		map.put(key, value);
		StringBuilder sb=new StringBuilder();
		for (Entry<String, String> entry : map.entrySet()) {
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		if (sb.charAt(sb.length()-1)=='&') {
			sb.delete(sb.length()-1, sb.length());
		}
		return sb.toString();
	}
}
