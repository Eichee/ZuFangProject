package com.zufang.tools;

public class AjaxResult {
	private String status;//
	private String msg; // 
	private Object data; // 

	public AjaxResult(String status, String msg, Object data) {
		super();
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public AjaxResult(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public AjaxResult(String status) {
		super();
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return CommonUtils.createGson().toJson(this);
	}
	
	public String toJson(){
		String result= toString();
		return result;
	}
}
