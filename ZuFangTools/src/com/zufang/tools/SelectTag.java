package com.zufang.tools;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SelectTag extends SimpleTagSupport {
	private String id;
	private String name;
	private String txtName;
	private String valueName;
	private Object items;
	private Object selectedValue;
	private String attributes;
	
	@Override
	public void doTag() throws JspException, IOException {
		/**
		 *  <select id='id' name='name' attributes><option value='val'>text</option></select>
		 */
		
		JspWriter out=getJspContext().getOut();
		out.print("<select id='");
		out.print(id);
		out.print("' name='");
		out.print(name);
		out.print("' ");
		if (attributes!=null) {
			out.print(attributes);
		}
		out.print(">");
		Iterable iterable;
		if (items.getClass().isArray()) {
			iterable=CommonUtils.toList(items);
		}
		else if (items instanceof Iterable) {
			iterable=(Iterable)items;
		}
		else{
			throw new IllegalArgumentException("items must be array or iterable");
		}
		for (Object item : iterable) {
			Object value=getPropertyValue(item,valueName);
			Object text=getPropertyValue(item, txtName);
			out.print("<option value='");
			out.print(value);
			out.print("' ");
			if (selectedValue!=null &&selectedValue.equals(value)) {
				out.print(" selected ");
			}
			out.print(">");
			out.print(text);
			out.print("</option>");
		}
		out.print("</select>");
	}
	
	
	private Object getPropertyValue(Object item, String propName) {
		try {
			BeanInfo beanInfo=Introspector.getBeanInfo(item.getClass());
			PropertyDescriptor[] propDescs=beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propertyDescriptor : propDescs) {
				if (propertyDescriptor.getName().equals(propName)) {
					Object value=propertyDescriptor.getReadMethod().invoke(item);
					return value;
				}
			}
			throw new IllegalArgumentException("can not find the field");
			
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		
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
	public String getTxtName() {
		return txtName;
	}
	public void setTxtName(String txtName) {
		this.txtName = txtName;
	}
	public String getValueName() {
		return valueName;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
	public Object getItems() {
		return items;
	}
	public void setItems(Object items) {
		this.items = items;
	}
	public Object getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(Object selectedValue) {
		this.selectedValue = selectedValue;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	
	
	
}
