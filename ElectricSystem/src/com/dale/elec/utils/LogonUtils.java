package com.dale.elec.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class LogonUtils {

	public static boolean checkNumber(HttpServletRequest request) {
		
		//获取验证码
		String checkNumber = request.getParameter("checkNumber"); 
		if(StringUtils.isBlank(checkNumber)){
			return false;
		}
		//从session中获取真实验证码
		String CHECK_NUMBER_KEY = (String) request.getSession().getAttribute("CHECK_NUMBER_KEY");
		if(StringUtils.isBlank(CHECK_NUMBER_KEY)){
			return false;
		}
		return checkNumber.equalsIgnoreCase(CHECK_NUMBER_KEY);
	}

	public static void remeberMe(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//创建2个Cookie
		//支持name有中文的形式
		name = URLEncoder.encode(name, "UTF-8");
		Cookie cookieName = new Cookie("name",name);
		Cookie cookiePassword = new Cookie("password",password);
		//设置Cookie的有效路径为根路径
		cookieName.setPath(request.getContextPath()+"/");
		cookiePassword.setPath(request.getContextPath()+"/");
		
		//设置cookie的有效时长
		//获取页面复选框的值
		String remeberMe = request.getParameter("remeberMe");
		//复选框被选中，那么设置cookie的时长为1个星期
		if(remeberMe!=null && remeberMe.equals("yes")){
			cookieName.setMaxAge(7*24*60*60);
			cookiePassword.setMaxAge(7*24*60*60);
		}
		else{
			cookieName.setMaxAge(0);
			cookiePassword.setMaxAge(0);
		}
		response.addCookie(cookieName);
		response.addCookie(cookiePassword);
		
	}

	
}
