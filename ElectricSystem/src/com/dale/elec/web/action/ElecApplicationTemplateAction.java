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

import com.dale.elec.service.IElecApplicationTemplateService;
import com.dale.elec.service.IElecProcessDefinitionService;
import com.dale.elec.web.form.ElecApplicationTemplateForm;
import com.dale.elec.web.form.ElecProcessDefinitionForm;
import com.opensymphony.xwork2.ModelDriven;

@Controller(value="elecApplicationTemplateAction")
@Scope(value="prototype")
public class ElecApplicationTemplateAction extends BaseAction implements ModelDriven<ElecApplicationTemplateForm> {

	//private ElecApplicationTemplateForm elecApplicationTemplateForm = new ElecApplicationTemplateForm();
	private ElecApplicationTemplateForm elecApplicationTemplateForm = new ElecApplicationTemplateForm();;
	public ElecApplicationTemplateForm getModel() {
		
		return elecApplicationTemplateForm;
	}

	@Resource(name=IElecApplicationTemplateService.SERVER_NAME)
	private IElecApplicationTemplateService elecApplicationTemplateService;
	
	@Resource(name=IElecProcessDefinitionService.SERVER_NAME)
	private IElecProcessDefinitionService elecProcessDefinitionService;
	/**  
	* 
	* 跳转到申请模板管理首页面
	*  跳转到applicationTemplateIndex.jsp
	*/
	public String home(){
		List<ElecApplicationTemplateForm> list = elecApplicationTemplateService.findApplicationTemplateList();
		//查询模版对象，放到request中。jsp页面使用sruts标签循环遍历
		request.setAttribute("templateList", list);
		return "home";
	}
	
	/**  
	* 
	* 跳转到新增申请模板页面
	* 跳转到applicationTemplateAdd.jsp
	*/
	public String add(){
		List<ProcessDefinition> list = elecProcessDefinitionService.findPDListByLastVersion();
		request.setAttribute("pdList", list);
		return "add";
	}
	/**  
	* 保存申请模板信息，页面传来的信息被封装到了VO对象中
	* 重定向到applicationTemplateList.jsp
	*/
	public String save(){
		elecApplicationTemplateService.saveApplicationTemplate(elecApplicationTemplateForm);
		return "list";
	}
	
	/**  
	* 
	* 跳转到编辑页面
	* 跳转到applicationTemplateEdit.jsp
	*/
	public String edit(){
		//通过id查询出修改模版的信息，elecApplicationTemplateForm默认被放到了栈顶
		elecApplicationTemplateForm = elecApplicationTemplateService
				.findApplicationTemplateByID(elecApplicationTemplateForm);
		//获取流程定义的集合
		List<ProcessDefinition> list = elecProcessDefinitionService.findPDListByLastVersion();
		request.setAttribute("pdList", list);
		return "edit";
	}
	
	/**  
	*
	* 修改保存申请模板信息
	*  重定向到applicationTemplateList.jsp
	*/
	public String update(){
		elecApplicationTemplateService.updateApplicationTemplate(elecApplicationTemplateForm);
		return "list";
	}
	
	/**  
	* 删除申请模板
	* 重定向到applicationTemplateList.jsp
	*/
	public String delete(){
		elecApplicationTemplateService.deleteApplicationTemplate(elecApplicationTemplateForm);
		return "list";
	}
	
	/**  
	* 附件下载
	* 使用struts2的返回类型stream
	*/
	public String download(){
		InputStream inputStream = elecApplicationTemplateService.downloadAttach(elecApplicationTemplateForm);
		//将inputStream放置到模型驱动VO对象中
		elecApplicationTemplateForm.setInputStream(inputStream);
		return "download";
	}
	
	
}
