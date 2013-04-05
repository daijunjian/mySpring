package com.dale.elec.service;

import java.util.List;

import com.dale.elec.web.form.ElecRoleForm;
import com.dale.elec.web.form.ElecUserForm;
import com.dale.elec.web.form.ElecXmlObject;

public interface IElecRoleService {

	final static String SERVER_NAME = "com.dale.elec.service.impl.ElecRoleServiceImpl";

	List<ElecXmlObject> readXml();

	List<ElecXmlObject> editXml(ElecRoleForm elecRoleForm);

	List<ElecUserForm> findUser(ElecRoleForm elecRoleForm);

	void saveRole(ElecRoleForm elecRoleForm);
}
