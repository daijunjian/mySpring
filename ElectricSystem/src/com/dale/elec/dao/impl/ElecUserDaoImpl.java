package com.dale.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dale.elec.dao.IElecUserDao;
import com.dale.elec.domain.ElecUser;

@Repository(IElecUserDao.SERVER_NAME)
public class ElecUserDaoImpl extends CommonDaoImpl<ElecUser> implements IElecUserDao{

	public List<Object[]> findElecRoleByLogonName(final String name) {
		final String sql = "SELECT b.ddlCode,b.ddlName FROM elec_user_role a " +
					 " LEFT OUTER JOIN elec_systemddl b ON a.RoleID = b.ddlCode AND b.keyword = '角色类型' " +
					 " INNER JOIN elec_user c ON a.UserID = c.UserID AND c.LogonName = ? ";
		@SuppressWarnings("unchecked")
		List<Object[]> list = (List<Object[]>)this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, name);
				return query.list();
			}
			
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Object> findElecPopedomByLogonName(final String name) {
		final String sql = "SELECT a.Popedomcode FROM elec_role_popedom a " +
					 " LEFT OUTER JOIN elec_user_role b ON a.RoleID = b.RoleID " +
					 " INNER JOIN elec_user c ON b.UserID = c.UserID AND c.LogonName = ?";
		List<Object> list = (List<Object>) this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				query.setParameter(0, name);
				return query.list();
			}
			
		});
		return list;
	}

	/**  
	* @Name: countUserByJctID
	* @Description: 使用所属单位进行分组，统计各个单位下的人员数量
	* @Return: List<Object[]>：统计结果（所属单位，北京，10人     所属单位，上海，20人）
	*/
	@SuppressWarnings("unchecked")
	public List<Object[]> countUserByJctID() {
		final String sql = "SELECT b.keyword,b.ddlName,COUNT(a.jctID) FROM elec_user a " +
					 " LEFT OUTER JOIN elec_systemddl b " +
					 " ON a.JctID = b.ddlCode AND b.keyword = '所属单位' " +
					 " GROUP BY a.JctID";
		List<Object[]> list = (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createSQLQuery(sql);
				return query.list();
			}
			
		});
		return list;
	}

}
