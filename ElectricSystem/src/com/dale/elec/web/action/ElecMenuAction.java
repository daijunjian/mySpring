package com.dale.elec.web.action;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dale.elec.domain.ElecUser;
import com.dale.elec.service.IElecCommonMsgService;
import com.dale.elec.service.IElecUserService;
import com.dale.elec.utils.LogonUtils;
import com.dale.elec.utils.MD5keyBean;
import com.dale.elec.web.form.ElecCommonMsgForm;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Controller(value="elecMenuAction")
@Scope(value="prototype")
public class ElecMenuAction extends BaseAction{

	@Resource(name=IElecCommonMsgService.SERVER_NAME)
	private IElecCommonMsgService elecCommonMsgService;
	
	@Resource(name=IElecUserService.SERVER_NAME)
	private IElecUserService elecUserService;
	
	public String home() throws UnsupportedEncodingException{
		//判断验证码输入是否有误
		/**
		 * flag=true：表示验证码输入正确
		 * flag=false：表示验证码输入错误
		 */
		boolean flag = LogonUtils.checkNumber(request);
		if(!flag){
			this.addFieldError("error", "验证码输入有误");
			return "error";
		}
		//验证登录名和密码
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		//使用登录名到数据库查询用户
		ElecUser elecUser = elecUserService.findElecUserByLogonName(name);
		if(elecUser==null){
			this.addFieldError("error", "登录名输入有误");
			return "error";
		}
		if(StringUtils.isBlank(password)){
			this.addFieldError("error", "登录密码不能为空");
			return "error";
		}
		MD5keyBean md5 = new MD5keyBean();
		String md5Password = md5.getkeyBeanofStr(password);
		if(!md5Password.equals(elecUser.getLogonPwd())){
			this.addFieldError("error", "输入登录密码有误");
			return "error";
		}
		//使用登录名获取角色信息，并封装到Hashtable中
		Hashtable<String, String> ht = elecUserService.findElecRoleByLogonName(name);
		if(ht == null){
			this.addFieldError("error", "当前用户没有分配角色");
			return "error";
			}
		//使用登录名获取权限信息，并封装到String字符串中
		String popedom = elecUserService.fincElecPopedomByLogonName(name);
		if(StringUtils.isBlank(popedom)){
			this.addFieldError("error", "当前用户的角色没有分配权限");
			return "error";
		}
		//添加记住我的功能
		LogonUtils.remeberMe(request,response);
		//放置session
		request.getSession().setAttribute("globle_user", elecUser);
		request.getSession().setAttribute("globle_role", ht);
		request.getSession().setAttribute("globle_popedom", popedom);
		return "home";
	}
	
	public String title(){
		return "title";
	}
	
	public String left(){
		return "left";
	} 
	
	public String change1(){
		return "change1";
	} 
	
	public String loading(){
		return "loading";
	} 
	
	public String alermXZ(){
		return "alermXZ";
	} 
	
	public String alermJX(){
		return "alermJX";
	} 
	
	public String alermZD(){
		ElecCommonMsgForm elecCommonMsgForm = elecCommonMsgService.findElecCommonMsg();
		//将对象放置到struts2的栈顶
		ActionContext.getContext().getValueStack().pop();
		ActionContext.getContext().getValueStack().push(elecCommonMsgForm);
		return "alermZD";
	} 
	
	
	public String alermSB(){
		ElecCommonMsgForm elecCommonMsgForm = elecCommonMsgService.findElecCommonMsg();
		//将对象放置到struts2的栈顶
		ActionContext.getContext().getValueStack().pop();
		ActionContext.getContext().getValueStack().push(elecCommonMsgForm);
		return "alermSB";
	} 
	
	public String alermYS(){
		return "alermYS";
	} 
	
	public String logout(){
		//清空session
		//request.getSession().removeAttribute("globle_user");//针对session名称清除
		//清空系统中所有的session
		request.getSession().invalidate();
		return "logout";
	}
	public String logouterror(){
		request.getSession().invalidate();
		return "logouterror";
	}
}
