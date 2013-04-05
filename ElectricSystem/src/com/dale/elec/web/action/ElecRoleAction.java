package com.dale.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dale.elec.service.IElecRoleService;
import com.dale.elec.service.IElecSystemDDLService;
import com.dale.elec.web.form.ElecRoleForm;
import com.dale.elec.web.form.ElecSystemDDLForm;
import com.dale.elec.web.form.ElecUserForm;
import com.dale.elec.web.form.ElecXmlObject;
import com.opensymphony.xwork2.ModelDriven;

@Controller(value="elecRoleAction")
@Scope(value="prototype")
@SuppressWarnings("serial")

public class ElecRoleAction extends BaseAction implements ModelDriven<ElecRoleForm>{

	@Resource(name=IElecRoleService.SERVER_NAME)
	private IElecRoleService elecRoleService;
	
	@Resource(name=IElecSystemDDLService.SREVER_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	private ElecRoleForm elecRoleForm = new ElecRoleForm();
	
	public ElecRoleForm getModel() {
		
		return elecRoleForm;
	}

	public String home(){
		List<ElecSystemDDLForm> roleList = elecSystemDDLService.findSystemDDLListByKeyword("角色类型");
		request.setAttribute("roleList", roleList);
		//从xml中获取权限
		List<ElecXmlObject> popedomList = elecRoleService.readXml();
		request.setAttribute("popedomList", popedomList);
		return "home";
	}
	
	public String edit(){
		
		//获取角色与权限的关联信息
		List<ElecXmlObject> popedomList = elecRoleService.editXml(elecRoleForm);
		request.setAttribute("popedomList", popedomList);
		//获取角色与用户的关联关系
		List<ElecUserForm> userList = elecRoleService.findUser(elecRoleForm);
		request.setAttribute("userList", userList);
		return "edit";
	}
	
	public String save(){
		elecRoleService.saveRole(elecRoleForm);
		return "save";
	}
	
}
