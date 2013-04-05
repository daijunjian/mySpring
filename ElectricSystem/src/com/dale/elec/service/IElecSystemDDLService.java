package com.dale.elec.service;

import java.util.List;

import com.dale.elec.web.form.ElecSystemDDLForm;

public interface IElecSystemDDLService {

	final static String SREVER_NAME = "com.dale.elec.service.impl.ElecSystemDDLServiceImpl";

	List<ElecSystemDDLForm> findKeyWord();

	List<ElecSystemDDLForm> findSystemDDLListByKeyword(String keyword);

	void saveSystemDDL(ElecSystemDDLForm elecSystemDDLForm);
}
