package com.dale.elec.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.aop.Pointcut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dale.elec.dao.IElecApplicationDao;
import com.dale.elec.dao.IElecApplicationTemplateDao;
import com.dale.elec.dao.IElecApproveInfoDao;
import com.dale.elec.dao.IElecSystemDDLDao;
import com.dale.elec.domain.ElecApplication;
import com.dale.elec.domain.ElecApplicationTemplate;
import com.dale.elec.domain.ElecApproveInfo;
import com.dale.elec.domain.ElecUser;
import com.dale.elec.service.IElecApplicationFlowService;
import com.dale.elec.utils.StringHelp;
import com.dale.elec.web.form.ElecApplicationFlowForm;

@Service(value=IElecApplicationFlowService.SERVER_MAME)
public class ElecApplicationFlowServiceImpl implements IElecApplicationFlowService{

	
	//注入两个dao
	@Resource(name=IElecApplicationDao.SERVER_NAME)
	private IElecApplicationDao elecApplicationDao;
	
	@Resource(name=IElecApproveInfoDao.SERVER_NAME)
	private IElecApproveInfoDao elecApproveInfoDao;
	
	@Resource(name=IElecApplicationTemplateDao.SERVER_NAME)
	private IElecApplicationTemplateDao elecApplicationTemplateDao;
	
	@Resource(name=IElecSystemDDLDao.SERVER_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;
	
	//注入流程引擎
	@Resource
	private ProcessEngine processEngine;

	


	/**  
	* 提交申请数据，保存申请信息，同时启动流程实例，并完成个人任务
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveApplication(
			ElecApplicationFlowForm elecApplicationFlowForm,
			HttpServletRequest request) {
		//保存申请信息
		ElecApplication elecApplication = this.applicationVOToPO(elecApplicationFlowForm,request);
		elecApplicationDao.save(elecApplication);
		//启动流程实例，同时设置流程变量，在审核过程中需要得到流程变量的值进行审核
		//定义流程变量
		Map<String, ElecApplication> variables = new HashMap<String, ElecApplication>();
		variables.put("application", elecApplication);//对应流程定义的xml中<task name="提交申请" g="180,104,133,64" assignee="#{application.applicationLogonName}">
		//使用模板ID查询模板信息表获取对应的流程定义的key
		ElecApplicationTemplate elecApplicationTemplate = elecApplicationTemplateDao.findObjectById(Long.parseLong(elecApplicationFlowForm.getApplicationTemplateID()));
		String processDefinitionKey = elecApplicationTemplate.getProcessDefinitionKey();
		ProcessInstance pi = processEngine.getExecutionService().startProcessInstanceByKey(processDefinitionKey,variables);
		//完成个人任务
		//使用流程实例ID查询任务
		Task task = processEngine.getTaskService()//
			     .createTaskQuery()//
			     .processInstanceId(pi.getId())//
			     .uniqueResult();
		processEngine.getTaskService().completeTask(task.getId());
	}

	/**申请信息VO转PO*/
	private ElecApplication applicationVOToPO(
			ElecApplicationFlowForm elecApplicationFlowForm,HttpServletRequest request) {
		ElecApplication elecApplication = new ElecApplication();
		//获取session中登录人的相关信息
		ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
		elecApplication.setApplicationUserID(elecUser.getUserID());
		elecApplication.setApplicationLogonName(elecUser.getLogonName());
		elecApplication.setApplicationUserName(elecUser.getUserName());
		//其他信息，设置模板ID
		elecApplication.setApplicationTemplateID(Long.parseLong(elecApplicationFlowForm.getApplicationTemplateID()));
		elecApplication.setApplyTime(new Date());
		elecApplication.setStatus(ElecApplication.APP_RUNNING);//审核状态为审核中
		//申请标题的格式：申请文件模板名称_申请人姓名_申请时间。
		elecApplication.setTitle(elecApplicationFlowForm.getApplicationTemplateName()+"_"+elecUser.getUserName()+"_"+StringHelp.convertDateToString(new Date()));
		elecApplication.setPath(StringHelp.uploadFile(elecApplicationFlowForm.getUpload()));//上传文件的同时，返回路径PATH
		return elecApplication;
	}

	public List<ElecApplicationFlowForm> findApplicationList(
			ElecApplicationFlowForm elecApplicationFlowForm, HttpServletRequest request) {
		String hqlWhere = "";
		List<Object> paramsList = new ArrayList<Object>();
		if(StringUtils.isNotBlank(elecApplicationFlowForm.getApplicationTemplateID())){
			hqlWhere += " and o.applicationTemplateID = ?";
			paramsList.add(Long.parseLong(elecApplicationFlowForm.getApplicationTemplateID()));
		}
		if(StringUtils.isNotBlank(elecApplicationFlowForm.getStatus())){
			hqlWhere += " and o.status = ?";
			paramsList.add(elecApplicationFlowForm.getStatus());
		}
		//按照当前用户ID查找申请表中对应的申请人用户ID(或者是登录名)
		ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
		hqlWhere += " and o.applicationUserID = ?";
		paramsList.add(elecUser.getUserID());
		Object [] params = paramsList.toArray();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.applyTime", "desc");
		List<ElecApplication> list = elecApplicationDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		List<ElecApplicationFlowForm> formList = this.applicationPOListToVOList(list);
		return formList;
	}

	private List<ElecApplicationFlowForm> applicationPOListToVOList(List<ElecApplication> list) {
		List<ElecApplicationFlowForm> formList = new ArrayList<ElecApplicationFlowForm>();
		for(int i=0;list!=null && i<list.size();i++){
			ElecApplication elecApplication = list.get(i);
			ElecApplicationFlowForm elecApplicationFlowForm = new ElecApplicationFlowForm();
			try {
				BeanUtils.copyProperties(elecApplicationFlowForm, elecApplication);
			} catch (Exception e) {
				throw new RuntimeException();
			} 
			elecApplicationFlowForm.setStatus(elecSystemDDLDao.findDdlName("审核状态", elecApplicationFlowForm.getStatus()));//审核状态，从数据字典中获取
			formList.add(elecApplicationFlowForm);
		}
		return formList;
	}

	/**  
	* 查询登录人的个人任务，同时获取申请的相关信息
	* List<ElecApplicationFlowForm> 申请信息列表
	*/
	public List<ElecApplicationFlowForm> findMyTaskWithApplicationList(HttpServletRequest request) {
		//获取当前登录人的信息
		ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
		String logonName = elecUser.getLogonName();
		List<ElecApplicationFlowForm> formlist = new ArrayList<ElecApplicationFlowForm>();
		//通过办理人查询我的个人任务
		List<Task> list = processEngine.getTaskService().findPersonalTasks(logonName);
		for(Task task:list){
			//获取流程变量
			ElecApplication elecApplication = (ElecApplication) processEngine.getTaskService().getVariable(task.getId(), "application");
			ElecApplicationFlowForm elecApplicationFlowForm = this.applicationPOToVO(elecApplication);
			elecApplicationFlowForm.setTaskID(task.getId());
			formlist.add(elecApplicationFlowForm);
		}
		return formlist;
	}
	
	/**值转换*/
	private ElecApplicationFlowForm applicationPOToVO(
			ElecApplication elecApplication) {
		ElecApplicationFlowForm elecApplicationFlowForm = new ElecApplicationFlowForm();
		try {
			BeanUtils.copyProperties(elecApplicationFlowForm, elecApplication);
		} catch (Exception e) {
			throw new RuntimeException();
		} 
		return elecApplicationFlowForm;
	}

	/**  
	* @Description: 获取当前任务所指向的连线的集合
	* @Parameters: ElecApplicationFlowForm：存放任务ID
	* @Return: Collection<String>:任务对应连线的集合
	*/
	public Collection<String> findOutcomdList(
			ElecApplicationFlowForm elecApplicationFlowForm) {
		String taskId = elecApplicationFlowForm.getTaskID();
		return processEngine.getTaskService().getOutcomes(taskId);
	}

	/** 
	* @Description: 文件下载，下载当前申请文件
	* @Parameters: ElecApplicationFlowForm：存放任务ID
	* @Return: Collection<String>:任务对应连线的集合
	*/
	public InputStream downloadAttach(
			ElecApplicationFlowForm elecApplicationFlowForm) {
		//使用ID获取Path
		Long applicationID = Long.parseLong(elecApplicationFlowForm.getApplicationID());
		ElecApplication elecApplication = elecApplicationDao.findObjectById(applicationID);
		InputStream inputStream = null;
		try {
			String path = elecApplication.getPath();
			//将name作为下载附件的名称
			String name = elecApplication.getTitle();
			try {
				name = new String(name.getBytes("gbk"),"iso8859-1");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException();
			}
			ServletActionContext.getRequest().setAttribute("fileName", name);
			//使用path找到upload下的资源文件
			File file = new File(path);
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new RuntimeException();
		}
		return inputStream;
	}

	/**  
	* @Description: 审批信息处理
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void approve(ElecApplicationFlowForm elecApplicationFlowForm,
			HttpServletRequest request) {
		//将VO对象转化成PO对象，保存到审核信息表
		ElecApproveInfo elecApproveInfo = this.approveInfoVOToPO(elecApplicationFlowForm,request);
		elecApproveInfoDao.save(elecApproveInfo);
		String taskId = elecApplicationFlowForm.getTaskID();
		String outcome = elecApplicationFlowForm.getOutcome();
		//使用任务ID获取当前任务的对象
		Task task = processEngine.getTaskService().getTask(taskId);//注意：此行代码一定要放在完成当前任务之前
		processEngine.getTaskService().completeTask(taskId, outcome);//完成当前的任务（并指定连线离开）
		//判断当前流程是否是最后一个流程(只需要判断当前流程实例是否为null)
		/**
		 * 使用流程实例ID来获取流程实例对象，
		 * 如果pi==null，说明当前流程实例已经结束
		 * 如果pi!=null，说明当前流程实例没有结束
		 */
		ProcessInstance pi = processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());
		//查找申请数据信息对象，用来改变status中的值
		ElecApplication elecApplication = elecApplicationDao.findObjectById(Long.parseLong(elecApplicationFlowForm.getApplicationID()));
		//判断点击的是“同意”还是“不同意”
		boolean approvel = elecApplicationFlowForm.isApproval();
		//同意
		if(approvel){
			if(pi==null){
				elecApplication.setStatus(ElecApplication.APP_PASS);//审核通过
			}
		}
		//不同意
		else{
			if(pi!=null){
				//当流程实例没有结束时，此时因为不同意，所以要强制结束该流程实例
				processEngine.getExecutionService().endProcessInstance(pi.getId(), ProcessInstance.STATE_ENDED);
			}
			elecApplication.setStatus(ElecApplication.APP_REJUCT);//审核不通过
		}
		elecApplicationDao.update(elecApplication);
	}

	/**VO对象转换成PO对象*/
	private ElecApproveInfo approveInfoVOToPO(
			ElecApplicationFlowForm elecApplicationFlowForm,HttpServletRequest request) {
		ElecApproveInfo elecApproveInfo = new ElecApproveInfo();
		elecApproveInfo.setApplicationID(Long.parseLong(elecApplicationFlowForm.getApplicationID()));//申请ID
		elecApproveInfo.setApproval(elecApplicationFlowForm.isApproval());//是否同意
		elecApproveInfo.setApproveTime(new Date());//审核时间
		ElecUser elecUser = (ElecUser) request.getSession().getAttribute("globle_user");
		elecApproveInfo.setApproveUserID(elecUser.getUserID());//用户ID
		elecApproveInfo.setApproveUserName(elecUser.getUserName());//用户姓名
		elecApproveInfo.setComment(elecApplicationFlowForm.getComment());//审核意见
		return elecApproveInfo;
	}

	/**  
	* @Description: 查询审核信息
	* @Parameters: ElecApplicationFlowForm：封装申请ID
	* @Return:  List<ElecApplicationFlowForm>：审核信息
	*/
	public List<ElecApplicationFlowForm> findFlowApprovedHidstory(
			ElecApplicationFlowForm elecApplicationFlowForm) {
		String hqlWhere = " and o.applicationID = ?";
		Object [] params = {Long.parseLong(elecApplicationFlowForm.getApplicationID())};
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.approveTime", "asc");
		List<ElecApproveInfo> list = elecApproveInfoDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		List<ElecApplicationFlowForm> formList = this.approveInfoPOListToVOList(list);
		return formList;
	}

	/**值转换*/
	private List<ElecApplicationFlowForm> approveInfoPOListToVOList(
			List<ElecApproveInfo> list) {
		List<ElecApplicationFlowForm> formList = new ArrayList<ElecApplicationFlowForm>();
		for(int i=0;list!=null && i<list.size();i++){
			ElecApproveInfo elecApproveInfo = list.get(i);
			ElecApplicationFlowForm elecApplicationFlowForm = new ElecApplicationFlowForm();
			try {
				BeanUtils.copyProperties(elecApplicationFlowForm, elecApproveInfo);
			} catch (Exception e) {
				throw new RuntimeException();
			} 
			formList.add(elecApplicationFlowForm);
		}
		return formList;
	}
}
