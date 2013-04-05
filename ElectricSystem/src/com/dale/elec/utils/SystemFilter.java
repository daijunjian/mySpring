package com.dale.elec.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dale.elec.domain.ElecUser;


public class SystemFilter implements Filter {

	// 不能被过滤的连接
	private List<String> list = new ArrayList<String>();

	public void init(FilterConfig arg0) throws ServletException {
		list.add("/index.jsp");
		list.add("/image.jsp");
		list.add("/system/elecMenuAction_home.do");
		list.add("/error.jsp");
		list.add("/system/elecMenuAction_logouterror.do");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path = request.getServletPath();
		// 如果当前的访问的连接与定义的需要放行的连接一致，要放行
		if (list != null && list.contains(path)) {
			chain.doFilter(request, response);
			return;
		}
		ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
		// 说明当前用户时正当访问，session中有值
		if (elecUser != null) {
			chain.doFilter(request, response);
			return;
		}
		//自动调整到首页面
		//response.sendRedirect(request.getContextPath() + "/index.jsp");
		//跳转到错误页面
		response.sendRedirect(request.getContextPath() + "/error.jsp");
	}

	public void destroy() {
		
	}
}
