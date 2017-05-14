package com.zufang.tools;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CheckBoxTag extends SimpleTagSupport{

	private String id;
	private String name;
	private String value;
	private String label;
	private boolean checked;
	
	@Override
	public void doTag() throws JspException, IOException {
		// <input type="checkbox" id="id" name="name" value="value"/><label for="id">label</label>
		JspWriter out=this.getJspContext().getOut();
		out.print("<input type='checkbox' id='");
		out.print(id);
		out.print("' name='");
		out.print(name);
		out.print("' value='");
		out.print(value);
		out.print("' ");
		if (checked) {
			out.print(" checked='checked' ");
		}
		out.print(" /><label for='");
		out.print(id);
		out.print("'>");
		out.print(label);
		out.print("</label>");
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
