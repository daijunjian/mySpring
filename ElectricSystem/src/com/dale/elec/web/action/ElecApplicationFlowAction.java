package com.dale.elec.web.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.dale.elec.service.IElecApplicationFlowService;
import com.dale.elec.service.IElecApplicationTemplateService;
import com.dale.elec.service.IElecSystemDDLService;
import com.dale.elec.web.form.ElecApplicationFlowForm;
import com.dale.elec.web.form.ElecApplicationTemplateForm;
import com.dale.elec.web.form.ElecSystemDDLForm;
import com.opensymphony.xwork2.ModelDriven;

@Controller(value="elecApplicationFlowAction")
@SuppressWarnings("serial")
public class ElecApplicationFlowAction extends BaseAction implements ModelDriven<ElecApplicationFlowForm>{

	private ElecApplicationFlowForm elecApplicationFlowForm = new ElecApplicationFlowForm();
	
	public ElecApplicationFlowForm getModel() {
		return elecApplicationFlowForm;
	}
	
	@Resource(name=IElecApplicationFlowService.SERVER_MAME)
	private IElecApplicationFlowService elecApplicationFlowService;
	
	@Resource(name=IElecApplicationTemplateService.SERVER_NAME)
	private IElecApplicationTemplateService elecApplicationTemplateService;
	
	@Resource(name=IElecSystemDDLService.SREVER_NAME)
	private IElecSystemDDLService elecSystemDDLService;
	
	//跳转到申请模版首页
	public String home(){
		List<ElecApplicationTemplateForm> list = elecApplicationTemplateService.findApplicationTemplateList();
		request.setAttribute("temList", list);
		return "home";
	}
	
	public String flowSubmitAdd() throws UnsupportedEncodingException{
		//处理模板名称的乱码
		String applicationTemplateName = elecApplicationFlowForm.getApplicationTemplateName();
		applicationTemplateName = new String(applicationTemplateName.getBytes("ISO-8859-1"),"UTF-8");
		elecApplicationFlowForm.setApplicationTemplateName(applicationTemplateName);
		return "flowSubmitAdd";
	}

	/**  
	* 保存申请的信息
	* 重定向到flowMyApplicationList.jsp
	*/
	public String saveApplication(){
		//保存申请信息表
		elecApplicationFlowService.saveApplication(elecApplicationFlowForm,request);
		return "list";
	}
	
	/**  
	* 
	* 我的申请查询首页面
	* 跳转到flowMyApplicationList.jsp
	*/
	public String myApplicationListHome(){
		//获取申请模板信息列表，用于下拉菜单的显示
		List<ElecApplicationTemplateForm> temList = elecApplicationTemplateService.findApplicationTemplateList();
		request.setAttribute("temList", temList);
		//获取审核状态信息列表，用于下拉菜单的显示（存放数据字典中）
		List<ElecSystemDDLForm> systemList = elecSystemDDLService.findSystemDDLListByKeyword("审核状态");
		request.setAttribute("systemList", systemList);
		//组织查询条件（申请模板、审核状态、申请人的ID），查询申请信息表
		List<ElecApplicationFlowForm> flowList = elecApplicationFlowService.findApplicationList(elecApplicationFlowForm,request);
		request.setAttribute("flowList", flowList);
		return "myApplicationListHome";
	}
	
	/**  
	* 我的任务查询（待我审批首页面显示）
	* 跳转到flowMyTaskList.jsp
	*/
	public String myTaskListHome(){
		List<ElecApplicationFlowForm> list = elecApplicationFlowService.findMyTaskWithApplicationList(request);
		request.setAttribute("taskList", list);
		return "myTaskListHome";
	}
	
	/**  
	*跳转到领导审核的页面
	* 跳转到flowApprove.jsp
	*/
	public String flowApprove(){
		//获取当前任务所指向的连线的集合
		Collection<String> collection = elecApplicationFlowService.findOutcomdList(elecApplicationFlowForm);
		request.setAttribute("collection", collection);
		return "flowApprove";
	}
	
	/**  
	* @Description: 下载申请文件
	*/
	public String download(){
		InputStream inputStream = elecApplicationFlowService.downloadAttach(elecApplicationFlowForm);
		//将inputStream放置到模型驱动VO对象中
		elecApplicationFlowForm.setInputStream(inputStream);
		return "download";
	}
	
	/**  
		重定向到flowMyTaskList.jsp
	*/
	public String approve(){
		elecApplicationFlowService.approve(elecApplicationFlowForm,request);
		return "tasklist";
	}
	
	/**  
	* 查看流程记录，重定向到flowApprovedHistory.jsp
	*/
	public String flowApprovedHistory(){
		List<ElecApplicationFlowForm> list = elecApplicationFlowService.findFlowApprovedHidstory(elecApplicationFlowForm);
		request.setAttribute("appList", list);
		return "flowApprovedHistory";
	}
}
