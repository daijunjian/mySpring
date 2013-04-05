package com.dale.elec.dao;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import com.dale.elec.domain.ElecRolePopedom;
import com.dale.elec.domain.ElecText;
import com.dale.elec.domain.ElecUser;
import com.dale.elec.utils.PageInfo;

public interface ICommonDao<T> {

	public void save(T entity);
	public void update(T entity);
	public T findObjectById(Serializable id);
	void deleteObjectByIds(Serializable... ids);
	void deleteObjectsByCollection(List<T> list);
	List<T> findCollectionByConditionNoPage(String hqlWhere,
			Object[] params, LinkedHashMap<String, String> orderby);
	void saveOrUpdateObject(T T);
	List<T> findCollectionByConditionWithPage(String hqlWhere, Object[] params,
			LinkedHashMap<String, String> orderBy, PageInfo pageInfo);
}
