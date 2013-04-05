package com.dale.elec.web.form;

import java.io.File;
import java.io.InputStream;

public class ElecProcessDefinitionForm {
	// 流程定义的ID
	private String id;
	// 流程定义的key
	private String key;

	// 上传工作流
	private File upload;

	// 用于查看流程图，将图片放置到inputStream中
	private InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
