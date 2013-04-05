package com.dale.elec.web.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dale.elec.service.IElecSystemDDLService;
import com.dale.elec.web.form.ElecSystemDDLForm;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller(value="elecSystemDDLAction")
@Scope(value="prototype")
public class ElecSystemDDLAction extends BaseAction implements ModelDriven<ElecSystemDDLForm>{

	private ElecSystemDDLForm elecSystemDDLForm = new ElecSystemDDLForm();
	
	public ElecSystemDDLForm getModel() {
		
		return elecSystemDDLForm;
	}

	@Resource(name=IElecSystemDDLService.SREVER_NAME)
	 private IElecSystemDDLService elecSystemDDLService;
	 
	public String home(){
		//System.err.println(elecSystemDDLForm);
		List<ElecSystemDDLForm> list = elecSystemDDLService.findKeyWord();
		request.setAttribute("systemList", list);
		//System.err.println(list.size());
		return "home";
	}
	
	public String edit(){
		//获取数据类型
		String keyword = elecSystemDDLForm.getKeyword();
		List<ElecSystemDDLForm> list = elecSystemDDLService.findSystemDDLListByKeyword(keyword);
		request.setAttribute("systemList",list);
		//System.err.println(list.size());
		return "edit";
	}
	
	//重定向
	public String save(){
		elecSystemDDLService.saveSystemDDL(elecSystemDDLForm);
		return "save";
	}
}
