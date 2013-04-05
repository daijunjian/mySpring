package com.dale.elec.service;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;

import com.dale.elec.web.form.ElecProcessDefinitionForm;

public interface IElecProcessDefinitionService {

	public static final String SERVER_NAME = "com.dale.elec.service.impl.ElecProcessDefinitionServiceImpl";

	List<ProcessDefinition> findPDListByLastVersion();

	void deployProcessDefinition(ZipInputStream zipInputStream);

	void deleteProcessDefination(ElecProcessDefinitionForm elecProcessDefinitionForm);

	InputStream findImageInputStreamByPDId(ElecProcessDefinitionForm elecProcessDefinitionForm);
	
}
