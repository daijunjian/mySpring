package com.dale.elec.web.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dale.elec.service.IElecCommonMsgService;
import com.dale.elec.web.form.ElecCommonMsgForm;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
@Controller(value="elecCommonMsgAction")
@Scope(value="prototype")
public class ElecCommonMsgAction extends BaseAction implements ModelDriven<ElecCommonMsgForm>{

	private ElecCommonMsgForm elecCommonMsgForm = new ElecCommonMsgForm();
	
	public ElecCommonMsgForm getModel() {
		return elecCommonMsgForm;
	}
	
	@Resource(name=IElecCommonMsgService.SERVER_NAME)
	private IElecCommonMsgService elecCommonMsgService;

	public String home(){
		//System.err.println("代办事宜");
		elecCommonMsgForm = elecCommonMsgService.findElecCommonMsg();
		//放入栈顶
		ActionContext.getContext().getValueStack().pop();
		ActionContext.getContext().getValueStack().push(elecCommonMsgForm);
		ValueStack valueStack = ActionContext.getContext().getValueStack();
		return "home";
	}
	
	public String save(){
		elecCommonMsgService.saveElecCommonMsg(elecCommonMsgForm);
		return "save";
	}
}
