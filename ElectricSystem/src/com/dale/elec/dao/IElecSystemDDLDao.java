package com.dale.elec.dao;

import java.util.List;

import com.dale.elec.domain.ElecSystemDDL;

public interface IElecSystemDDLDao  extends ICommonDao<ElecSystemDDL>{

	final static String SERVER_NAME = "com.dale.elec.dao.impl.ElecSystemDDLDaoImpl";
	public List<Object> findKeyWord();
	public String findDdlName(String string, String sexID);
}
