package com.dale.elec.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dale.elec.dao.IElecSystemDDLDao;
import com.dale.elec.domain.ElecSystemDDL;

@Repository(value=IElecSystemDDLDao.SERVER_NAME)
public class ElecSystemDDLDaoImpl extends CommonDaoImpl<ElecSystemDDL> implements IElecSystemDDLDao {

	
	public List<Object> findKeyWord() {
		String hql = "SELECT DISTINCT o.keyword FROM ElecSystemDDL o";
		List<Object> list = this.getHibernateTemplate().find(hql);
		return list;
	}

	public String findDdlName(String keyword, String ddlCode) {
		String hql = "select o.ddlName from ElecSystemDDL o " +
			     " where o.keyword = '"+keyword+"'" +
			     " and o.ddlCode = "+ddlCode;
		List<Object> list = this.getHibernateTemplate().find(hql);
		String ddeName = "";
		if(list!=null && list.size()>0){
			Object object = list.get(0);
			ddeName = object.toString();
		}
		return ddeName;
	}

}
