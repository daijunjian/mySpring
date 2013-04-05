package com.dale.elec.web.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoaderListener;


import com.dale.elec.domain.ElecText;
import com.dale.elec.service.IElecTextService;
import com.dale.elec.utils.StringHelp;
import com.dale.elec.web.form.ElecTextForm;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

@Controller(value="elecTextAction")
@Scope(value="prototype")
public class ElecTextAction extends BaseAction implements ModelDriven{

	private ElecTextForm elecTextForm = new ElecTextForm();
	
	@Resource(name=IElecTextService.SERVER_NAME)
	private IElecTextService service;
	
	public ElecTextForm getModel() {
		return elecTextForm;
	}

	//赋值，取值，回显
	public String save(){
		ElecText elecText = new ElecText();
		elecText.setTextName(elecTextForm.getTextName());
		elecText.setTextDate(StringHelp.convertStringToDate(elecTextForm.getTextDate()));
		elecText.setTextRemark(elecTextForm.getTextRemark());
		service.save(elecText);
		return "save";
	}
	
	
}
