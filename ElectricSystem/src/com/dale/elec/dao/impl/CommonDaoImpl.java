package com.dale.elec.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;


import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dale.elec.dao.ICommonDao;
import com.dale.elec.domain.ElecText;
import com.dale.elec.utils.GenericSuperclass;
import com.dale.elec.utils.PageInfo;
import com.mchange.v2.c3p0.ComboPooledDataSource;

//注入sessionfactory，需要实现hibernateDaoSupport
public class CommonDaoImpl<T> extends HibernateDaoSupport implements ICommonDao<T>{

	@SuppressWarnings("rawtypes")
	Class entityClass = GenericSuperclass.ConverType(this.getClass());
	
	//注入sessionfactoy
	@Resource(name="sessionFactory")
	public void setSessionFactoryDI(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

	//定义一个全局的变量，来记住模版
	
	public void save(T entity) {
//		import org.apache.tomcat.dbcp.dbcp.BasicDataSourceFactory;
//		BasicDataSourceFactory d = new BasicDataSourceFactory();
//		ComboPooledDataSource com = new ComboPooledDataSource("");
		this.getHibernateTemplate().save(entity);
		
	}
	
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}
	
	@SuppressWarnings("unchecked")
	public T findObjectById(Serializable id) {
		return (T) this.getHibernateTemplate().get(entityClass, id);
	}

	public void deleteObjectByIds(Serializable... ids) {
		for (int i = 0; i < ids.length; i++) {
			Object object = this.getHibernateTemplate().get(entityClass, ids[i]);
			this.getHibernateTemplate().delete(object);
		}
		
	}


	public void deleteObjectsByCollection(List<T> list) {
		this.getHibernateTemplate().deleteAll(list);
		
	}
	
	/**使用查询条件和排序组织查询条件用来查询对应的集合列表数据，不实现分页*/
	public List<T> findCollectionByConditionNoPage(String hqlWhere,
			final Object[] params, LinkedHashMap<String, String> orderby) {
		/**
		 *  SELECT * FROM elec_text o WHERE 1=1     #DAO层封装
			AND o.textName LIKE '%张%'              #Service层封装
			AND o.textRemark LIKE '%张%'            #Service层封装
			ORDER BY o.textDate DESC,o.textName ASC #Service层封装
		 */
		//System.err.println("3");
		String hql = "from " + entityClass.getSimpleName() + " o where 1=1";
		//处理排序，返回添加排序条件后hql语句
		String hqlOrder = this.organizeOrderByCondition(orderby);
		final String finalHql = hql + hqlWhere + hqlOrder;
		//第一种方式：
		//List<T> list = this.getHibernateTemplate().find(finalHql, params);
		//第二种方式:调用hibernateTemplate底层
		//System.err.println("6");
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				System.err.println(finalHql);
				Query query = session.createQuery(finalHql); //webLogic不支持HQL语句
				for(int i=0;params!=null && i<params.length;i++){
					query.setParameter(i, params[i]);
				}
				return query.list();
			}
		});
		return list;
	}

	/**使用传递的参数，组织排序条件*/
	private String organizeOrderByCondition(
			LinkedHashMap<String, String> orderby) {
		/**ORDER BY o.textDate DESC,o.textName ASC #Service层封装*/
		StringBuffer buffer = new StringBuffer();
		if(orderby!=null){
			buffer.append(" ORDER BY ");
			for(Map.Entry<String, String> map:orderby.entrySet()){
				buffer.append(map.getKey()+" "+map.getValue()+",");
			}
			buffer.deleteCharAt(buffer.length()-1);//删除最后一个位置的,
		}
		//System.err.println("5");
		return buffer.toString();
	}


	/**如果是临时对象执行save方法，如果是游离对象或持久对象执行update方法*/
	public void saveOrUpdateObject(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> findCollectionByConditionWithPage(String hqlWhere, final Object[] params,
			LinkedHashMap<String, String> orderBy, final PageInfo pageInfo) {
		/**
		 *  SELECT * FROM elec_text o WHERE 1=1     #DAO层封装
			AND o.textName LIKE '%张%'              #Service层封装
			AND o.textRemark LIKE '%张%'            #Service层封装
			ORDER BY o.textDate DESC,o.textName ASC #Service层封装
		 */
		String hql = "from " + entityClass.getSimpleName() + " o where 1=1";
		//处理排序，返回添加排序条件后hql语句
		String hqlOrder = this.organizeOrderByCondition(orderBy);
		final String finalHql = hql + hqlWhere + hqlOrder;
		//第一种方式：
		//List<T> list = this.getHibernateTemplate().find(finalHql, params);
		//第二种方式:调用hibernateTemplate底层
		List<T> list = (List<T>) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(finalHql);
				for(int i=0;params!=null && i<params.length;i++){
					query.setParameter(i, params[i]);
				}
				//实现分页：begin
				pageInfo.setTotalResult(query.list().size());//设置总的记录数
				query.setFirstResult(pageInfo.getBeginResult());//指的是当前页从第几条开始检索（指的是索引，从0开始）
				query.setMaxResults(pageInfo.getPageSize());//指的是当前页显示几条记录
				return query.list();
				//end
			}
		});
		return list;
	}
}
