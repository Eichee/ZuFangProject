package com.zufang.tools;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PagerTag extends SimpleTagSupport{

	private long totalCount;
	private int pageSize;
	private int currentPageIndex;
	private String urlFormat;
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPageIndex() {
		return currentPageIndex;
	}
	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
	}
	public String getUrlFormat() {
		return urlFormat;
	}
	public void setUrlFormat(String urlFormat) {
		this.urlFormat = urlFormat;
	}
	
	public String getUrl(long pageNum){
		return urlFormat.replace("{pageNum}", String.valueOf(pageNum));
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out=this.getJspContext().getOut();
		out.print("<a href='");
		out.print(getUrl(1));
		out.print("'>首页</a>");
		
		//第一个页码
		long firstPageNum=Math.max(currentPageIndex-5, 1);
		//总页数
		long totalPageNum=(long)Math.ceil(totalCount*1.0f/pageSize);
		//最后一个页码
		long lastPageNum=Math.min(totalPageNum, currentPageIndex+4);
		
		for (long i = firstPageNum; i <= lastPageNum; i++) {
			if (i!=currentPageIndex) {
				out.print("<a href='");
				out.print(getUrl(i));
				out.print("/>");
				out.print(i);
				out.print("</a>");
			}
			else
			{
				out.print("<span>");
				out.print(i);
				out.print("</span");
			}
			
		}
		
		out.print("<a href='");
		out.print(getUrl(totalPageNum));
		System.out.println(totalPageNum);
		out.print("'>末页</a>");
	}
}
