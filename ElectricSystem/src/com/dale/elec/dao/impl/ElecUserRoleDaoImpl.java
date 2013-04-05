package com.dale.elec.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dale.elec.dao.IElecUserRoleDao;
import com.dale.elec.domain.ElecUserRole;

@Repository(value=IElecUserRoleDao.SERVER_NAME)
public class ElecUserRoleDaoImpl extends CommonDaoImpl<ElecUserRole> implements IElecUserRoleDao{

	@SuppressWarnings("unchecked")
	public List<Object[]> findUserByRoleID(final String roleid) {
		final String sql = "SELECT DISTINCT CASE b.roleID WHEN ? THEN '1' ELSE '2' END AS flag, " +
					 " a.userID AS userid,a.logonName AS logonname,a.username AS username " +
					 " FROM elec_user a " +
					 " LEFT OUTER JOIN elec_user_role b ON a.userID = b.userID " +
					 " AND b.roleID = ?" +
					 " Where a.isDuty = '1'";
		List<Object[]> list = (List<Object[]>) this.getHibernateTemplate().execute(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				@SuppressWarnings("deprecation")
				Query query = session.createSQLQuery(sql)
				           .addScalar("flag", Hibernate.STRING)
				           .addScalar("userid", Hibernate.STRING)
				           .addScalar("logonname", Hibernate.STRING)
				           .addScalar("username", Hibernate.STRING);
				query.setParameter(0, roleid);
				query.setParameter(1, roleid);
				return query.list();
			}
			
		});
		return list;
	}
}
