package com.dale.elec.service.impl;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dale.elec.service.IElecProcessDefinitionService;
import com.dale.elec.web.form.ElecProcessDefinitionForm;

@Service(value=IElecProcessDefinitionService.SERVER_NAME)
@Transactional(readOnly=true)
public class ElecProcessDefinitionServiceImpl implements IElecProcessDefinitionService{

	
	@Resource
	private ProcessEngine processEngine; //流程引擎
	/**
	 * 获取最新版本的流程定义,封装流程定义到集合
	 */
	public List<ProcessDefinition> findPDListByLastVersion() {
		List<ProcessDefinition> list = processEngine.getRepositoryService()//
					    .createProcessDefinitionQuery()//
					    //降序排序，把最新版本的流程定义放到后面
					    .orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION)//
					    .list();
		//使用likedHashMap的好处，它可以去重复，有相同的key，后面最新版本的会覆盖掉前面的版本
		Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();
		for(ProcessDefinition pd:list){
			map.put(pd.getKey(), pd);  //后面的流程定义会覆盖前面的流程版本
		}
		List<ProcessDefinition> pdlist = new ArrayList<ProcessDefinition>(map.values());
		return pdlist;
	}

	/**  
	* 
	* 使用zip格式部署流程定义
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deployProcessDefinition(ZipInputStream zipInputStream) {
		//部署流程定义
		processEngine.getRepositoryService()//
		     .createDeployment()//
		     .addResourcesFromZipInputStream(zipInputStream)//
		     .deploy();
	}

	/**  
	* 
	* 级联删除流程定义，使用流程定义的key，删除key下所有的版本
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteProcessDefination(
			ElecProcessDefinitionForm elecProcessDefinitionForm) {
		String key = elecProcessDefinitionForm.getKey();
		//处理乱码
		try {
			key = new String(key.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
		//使用流程定义的key查询流程定义列表
		List<ProcessDefinition> list = processEngine.getRepositoryService()//
								.createProcessDefinitionQuery()//
								.processDefinitionKey(key)//按照流程定义查询
								.list();
		for(ProcessDefinition pd:list){
			//从对象中获取deploymentID
			String deploymentId = pd.getDeploymentId();
			//根据deploymentID,级联删除流程定义
			processEngine.getRepositoryService().deleteDeploymentCascade(deploymentId);
		}
		
	}

	/**  
	* findImageInputStreamByPDId
	* 查看流程图，使用流程定义ID获取流程部署对象和资源图片名称
	* ElecProcessDefinitionForm 封装流程对应的id
	* InputStream，封装图片的inputStream流
	*/
	public InputStream findImageInputStreamByPDId(
			ElecProcessDefinitionForm elecProcessDefinitionForm) {
		//获取流程定义ID
		String processDefinitionId = elecProcessDefinitionForm.getId();
		try {
			processDefinitionId = new String(processDefinitionId.getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
		//使用流程定义ID获取流程定义对象
		ProcessDefinition pd = processEngine.getRepositoryService()//
					   .createProcessDefinitionQuery()//
					   .processDefinitionId(processDefinitionId)//
					   .uniqueResult();
		//部署流程对象ID
		String deploymentId = pd.getDeploymentId();
		//获取资源图片的名称
		String resourceName = pd.getImageResourceName();
		//获取inputStream流，用来存储图片
		InputStream in = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
		return in;
	}	
	
}
