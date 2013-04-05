package com.dale.elec.domain;

public class ElecApplicationTemplate {

	/**
	 * create table Elec_Application_Template ( id long not null, #主键ID name
	 * varchar(500),#名称 processDefinitionKey varchar(500),#流程定义的key path
	 * varchar(5000)#上传的模板文件的存储位置
	 */

	private Long id;
	private String name;
	private String processDefinitionKey;
	private String path;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
