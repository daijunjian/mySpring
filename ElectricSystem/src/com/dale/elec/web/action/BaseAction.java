package com.dale.elec.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	public void setServletRequest(HttpServletRequest req) {
		
		request = req;
	}

	public void setServletResponse(HttpServletResponse res) {
		
		response = res;
	}
	
	
}
