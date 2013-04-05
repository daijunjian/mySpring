package com.dale.elec.dao;

import java.util.List;

import com.dale.elec.domain.ElecUserRole;

public interface IElecUserRoleDao extends ICommonDao<ElecUserRole>{

	final static String SERVER_NAME = "com.dale.elec.dao.impl.ElecUserRoleDaoImpl";

	List<Object[]> findUserByRoleID(String roleid);
}
