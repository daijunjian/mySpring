package com.dale.elec.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dale.elec.dao.IElecApplicationTemplateDao;
import com.dale.elec.domain.ElecApplicationTemplate;
import com.dale.elec.service.IElecApplicationTemplateService;
import com.dale.elec.utils.StringHelp;
import com.dale.elec.web.form.ElecApplicationTemplateForm;

@Service(value=IElecApplicationTemplateService.SERVER_NAME)
public class ElecApplicationTemplateServiceImpl implements IElecApplicationTemplateService{

	@Resource
	private ProcessEngine processEngine;

	@Resource(name=IElecApplicationTemplateDao.SERVER_NAME)
	private IElecApplicationTemplateDao elecApplicationTemplateDao;
	
	//查询模版对象，调用底层的findCollectionByConditionNoPage方法
	public List<ElecApplicationTemplateForm> findApplicationTemplateList() {
		List<ElecApplicationTemplate> list = elecApplicationTemplateDao.findCollectionByConditionNoPage("", null, null);
		List<ElecApplicationTemplateForm> formList = this.elecApplicationPOListToVOList(list);
		return formList;
	}

	/**值转换，POListToVOList*/
	private List<ElecApplicationTemplateForm> elecApplicationPOListToVOList(
			List<ElecApplicationTemplate> list) {
		List<ElecApplicationTemplateForm> formList = new ArrayList<ElecApplicationTemplateForm>();
		for(int i=0;list!=null && i<list.size();i++){
			ElecApplicationTemplate elecApplicationTemplate = list.get(i);
			ElecApplicationTemplateForm elecApplicationTemplateForm = new ElecApplicationTemplateForm();
			try {
				BeanUtils.copyProperties(elecApplicationTemplateForm, elecApplicationTemplate);
			} catch (Exception e) {
				throw new RuntimeException();
			} 
			formList.add(elecApplicationTemplateForm);
		}
		return formList;
	}

	/**  
	* 保存申请模板信息，同时上传附件
	* ElecApplicationTemplateForm 存放：模板名称，流程定义key，上传的文件
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveApplicationTemplate(
			ElecApplicationTemplateForm elecApplicationTemplateForm) {
		//文件的上传，上传到upload的文件夹下，同时返回上传文件的路径
		File file = elecApplicationTemplateForm.getUpload();
		//保存到数据库之前要先 上传附件
		String path = StringHelp.uploadFile(file);
		elecApplicationTemplateForm.setPath(path);
		//组织PO对象
		ElecApplicationTemplate elecApplicationTemplate = this.applicationTemplateVOToPO(elecApplicationTemplateForm);
		elecApplicationTemplateDao.save(elecApplicationTemplate);
	}

	/**值转换，从VO对象到PO对象*/
	private ElecApplicationTemplate applicationTemplateVOToPO(
			ElecApplicationTemplateForm elecApplicationTemplateForm) {
		ElecApplicationTemplate elecApplicationTemplate = new ElecApplicationTemplate();
		try {
			BeanUtils.copyProperties(elecApplicationTemplate, elecApplicationTemplateForm);
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return elecApplicationTemplate;
	}

	/**  
	* 使用主键ID查询申请模板信息
	* ElecApplicationTemplateForm 存放申请模板主键ID
	* ElecApplicationTemplateForm VO对象
	*/
	public ElecApplicationTemplateForm findApplicationTemplateByID(
			ElecApplicationTemplateForm elecApplicationTemplateForm) {
		Long templateId = Long.parseLong(elecApplicationTemplateForm.getId());
		ElecApplicationTemplate elecApplicationTemplate = elecApplicationTemplateDao.findObjectById(templateId);
		elecApplicationTemplateForm = this.applicationTemplatePOToVO(elecApplicationTemplate,elecApplicationTemplateForm);
		return elecApplicationTemplateForm;
	}

	/**值转换，PO对象转成VO对象*/
	private ElecApplicationTemplateForm applicationTemplatePOToVO(
			ElecApplicationTemplate elecApplicationTemplate,
			ElecApplicationTemplateForm elecApplicationTemplateForm) {
		try {
			BeanUtils.copyProperties(elecApplicationTemplateForm, elecApplicationTemplate);
		} catch (Exception e) {
			throw new RuntimeException();
		} 
		return elecApplicationTemplateForm;
	}

	/**  
	* 根据传递的参数修改申请模板
	* ElecApplicationTemplateForm 存放id、path、模板名称、流程定义key、上传文件upload
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void updateApplicationTemplate(
			ElecApplicationTemplateForm elecApplicationTemplateForm) {
		//获取旧的文件的路径
		String path = elecApplicationTemplateForm.getPath();
		File upload = elecApplicationTemplateForm.getUpload();
		//说明要删除旧的附件，并上传新的附件
		if(upload!=null){
			File oldFile = new File(path);
			if(oldFile.exists()){
				//删除旧的文件
				oldFile.delete();
			}
			//上传新的文件
			String newPath = StringHelp.uploadFile(upload);
			//路径改成新的路径
			elecApplicationTemplateForm.setPath(newPath);
		}
		//说明并没有上传附件，数据库中仍然存放之前的附件，路径不变
		else{
			
		}
		ElecApplicationTemplate elecApplicationTemplate = this.applicationTemplateVOToPO(elecApplicationTemplateForm);
		elecApplicationTemplateDao.update(elecApplicationTemplate);
	}

	/**  
	* 删除申请模板，同时删除对应的附件
	* ElecApplicationTemplateForm 存放id
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void deleteApplicationTemplate(
			ElecApplicationTemplateForm elecApplicationTemplateForm) {
		Long templateId = Long.parseLong(elecApplicationTemplateForm.getId());
		//使用id获取path
		ElecApplicationTemplate elecApplicationTemplate = elecApplicationTemplateDao.findObjectById(templateId);
		//获取路径，使用路径删除文件
		String path = elecApplicationTemplate.getPath();
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		//删除对应数据库中的数据
		elecApplicationTemplateDao.deleteObjectByIds(templateId);
	}

	/**  
	*  文件下载，将下载的文件放置到Inputstream中
	*ElecApplicationTemplateForm 存放id
	*/
	public InputStream downloadAttach(
			ElecApplicationTemplateForm elecApplicationTemplateForm) {
		//使用ID获取Path
		Long templateId = Long.parseLong(elecApplicationTemplateForm.getId());
		ElecApplicationTemplate elecApplicationTemplate = elecApplicationTemplateDao.findObjectById(templateId);
		InputStream inputStream = null;
		try {
			String path = elecApplicationTemplate.getPath();
			//将name作为下载附件的名称
			String name = elecApplicationTemplate.getName();
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
	

	
}
