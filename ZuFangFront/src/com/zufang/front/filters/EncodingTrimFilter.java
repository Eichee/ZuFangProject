package com.zufang.front.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.zufang.tools.TrimHttpRequest;

@WebFilter("/*")
public class EncodingTrimFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		HttpServletRequest request=(HttpServletRequest)req;
		TrimHttpRequest trimReq=new TrimHttpRequest(request);
		chain.doFilter(trimReq, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
