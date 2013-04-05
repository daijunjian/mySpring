package com.dale.elec.service;

import com.dale.elec.domain.ElecText;
import com.dale.elec.web.form.ElecTextForm;

public interface IElecTextService {

	final static String SERVER_NAME = "com.dale.elec.service.impl.ElecTextServiceImpl";
	public void save(ElecText entity);
	public void findCollectionByConditionNoPage(ElecTextForm elecTextForm);
}
