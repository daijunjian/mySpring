package com.dale.elec.service;

import java.io.InputStream;
import java.util.List;

import com.dale.elec.web.form.ElecApplicationTemplateForm;

public interface IElecApplicationTemplateService {

	public static final String SERVER_NAME = "com.dale.elec.service.impl.ElecApplicationTemplateServiceImpl";

	List<ElecApplicationTemplateForm> findApplicationTemplateList();

	void saveApplicationTemplate(ElecApplicationTemplateForm elecApplicationTemplateForm);

	ElecApplicationTemplateForm findApplicationTemplateByID(
			ElecApplicationTemplateForm elecApplicationTemplateForm);

	void updateApplicationTemplate(ElecApplicationTemplateForm elecApplicationTemplateForm);

	void deleteApplicationTemplate(ElecApplicationTemplateForm elecApplicationTemplateForm);

	InputStream downloadAttach(ElecApplicationTemplateForm elecApplicationTemplateForm);

}
