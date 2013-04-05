package com.dale.elec.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.dale.elec.service.IElecProcessDefinitionService;
import com.dale.elec.web.form.ElecProcessDefinitionForm;
import com.opensymphony.xwork2.ModelDriven;

@SuppressWarnings("serial")
@Controller(value="elecProcessDefinitionAction")
@Scope(value="prototype")
public class ElecProcessDefinitionAction extends BaseAction implements ModelDriven<ElecProcessDefinitionForm>{

	private ElecProcessDefinitionForm elecProcessDefinitionForm = new ElecProcessDefinitionForm();
	
	@Resource(name=IElecProcessDefinitionService.SERVER_NAME)
	private IElecProcessDefinitionService elecProcessDefinitionService;

	public ElecProcessDefinitionForm getModel() {
		return elecProcessDefinitionForm;
	}
	
	/**
	 * 跳转到流程管理首页面
	 */
	public String home(){
		List<ProcessDefinition> list = elecProcessDefinitionService.findPDListByLastVersion();
		//把获得的最新版本的流程定义放到request中，jsp页面使用struts标签，迭代显示在页面上
		request.setAttribute("pdlist", list);
		return "home";
	}
	
	/*
	 * 跳转到工作流
	 */
	public String add(){
		return "add";
	}
	
	/**
	 * 部署工作流
	 */
	public String save(){
		//将file转换成ZipInputStream
		File file = elecProcessDefinitionForm.getUpload();
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			elecProcessDefinitionService.deployProcessDefinition(zipInputStream);
		} catch (FileNotFoundException e) {
			throw new RuntimeException();
		}
		return "list";
	}
	
	/**  
	* 根据key删除流程定义
	* 重定向到processDifinitionList.jsp
	*/
	public String delete(){
		elecProcessDefinitionService.deleteProcessDefination(elecProcessDefinitionForm);
		return "list";
	}
	
	/*
	* 查看流程图
	* 使用stream显示图片
	*/
	public String downloadProcessImage(){
		InputStream in = elecProcessDefinitionService.findImageInputStreamByPDId(elecProcessDefinitionForm);
		//使用struts2的模型驱动，设置inputStream，用于显示图片
		elecProcessDefinitionForm.setInputStream(in);
		return "downloadProcessImage";
	}
}
