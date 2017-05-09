package com.zufang.tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class TrimHttpRequest extends HttpServletRequestWrapper{

	public TrimHttpRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		String value=super.getParameter(name);
		if (value==null) {
			return value;
		}
		return ToDBC(value.trim());
	}

	/**
	 * 全角转半角
	 * @param value
	 * @return
	 */
	public static String ToDBC(String value) {
		char chars[] = value.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == '\u3000')
			{
				chars[i] = ' ';
			} else if (chars[i] > '\uFF00' && chars[i] < '\uFF5F')
			{
				chars[i] = (char) (chars[i] - 65248);
			}
		}
		return new String(chars);
	}
}
