package com.dale.elec.service;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dale.elec.domain.ElecUser;
import com.dale.elec.web.form.ElecUserForm;

public interface IElecUserService {

	final static String SERVER_NAME = "com.dale.elec.service.impl.ElecUserServiceImpl";

	public List<ElecUserForm> findElecUserListByCondition(ElecUserForm elecUserForm,HttpServletRequest request);

	public void saveElecUser(ElecUserForm elecUserForm);

	public ElecUserForm findElecUserByID(ElecUserForm elecUserForm);

	public String checkUser(String logonName);
	public void deleteElecUser(ElecUserForm elecUserForm);

	public ElecUser findElecUserByLogonName(String name);

	public Hashtable<String, String> findElecRoleByLogonName(String name);

	public String fincElecPopedomByLogonName(String name);

	public ArrayList<String> exportExcelName();

	public ArrayList<ArrayList<String>> exportExcelData(ElecUserForm elecUserForm);

	public void importExcelDataToBase(ArrayList<String[]> list);

	public List<Object[]> countUserByJctID();

}
