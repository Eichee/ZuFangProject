package com.zufang.tools;

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
}
