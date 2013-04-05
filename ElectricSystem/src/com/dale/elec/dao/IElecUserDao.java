package com.dale.elec.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.dale.elec.domain.ElecUser;
import com.dale.elec.utils.PageInfo;

public interface IElecUserDao extends ICommonDao<ElecUser> {

	final static String SERVER_NAME = "com.dale.elec.dao.impl.ElecUserDaoImpl";

	List<Object[]> findElecRoleByLogonName(String name);

	List<Object> findElecPopedomByLogonName(String name);

	List<Object[]> countUserByJctID();

}
