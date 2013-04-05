package com.dale.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dale.elec.dao.IElecTextDao;
import com.dale.elec.domain.ElecText;
import com.dale.elec.service.IElecTextService;
import com.dale.elec.web.form.ElecTextForm;

@Service(IElecTextService.SERVER_NAME)
@Transactional(readOnly=true)
public class ElecTextServiceImpl implements IElecTextService {

	@Resource
	private IElecTextDao elecTextDao;
	
	@Transactional(readOnly=false,isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED )
	public void save(ElecText entity) {
		elecTextDao.save(entity);
	}


	/**
	 * 通过条件组织查询条件，查询对应的结果集，结果集不分页
	 */
	public void findCollectionByConditionNoPage(ElecTextForm elecTextForm) {
		//1、获取VO对象中的查询参数的值
		String textName = elecTextForm.getTextName();
		String textRemark = elecTextForm.getTextRemark();
		//2、组织查询的条件，封装到hqlWhere和params中
		/**
		 *  SELECT * FROM elec_text o WHERE 1=1     #DAO层封装
			AND o.textName LIKE '%张%'              #Service层封装
			AND o.textRemark LIKE '%张%'            #Service层封装
			ORDER BY o.textDate DESC,o.textName ASC #Service层封装
		 */
		String hqlWhere = "";
		List<String> paramsList = new ArrayList<String>();
		if(StringUtils.isNotBlank(textName)){
			hqlWhere += " AND o.textName LIKE ?";
			paramsList.add("%"+textName+"%");
		}
		if(StringUtils.isNotBlank(textRemark)){
			hqlWhere += " AND o.textRemark LIKE ?";
			paramsList.add("%"+textRemark+"%");
		}
		Object [] params = paramsList.toArray();
		//3、组织排序的条件,orderby
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.textDate", "DESC");
		orderby.put("o.textName", "ASC");
		List<ElecText> list = elecTextDao.findCollectionByConditionNoPage(hqlWhere,params,orderby);
		for(int i=0;list!=null && i<list.size();i++){
			ElecText elecText = list.get(i);
			System.out.println(elecText.getTextName()+"     "+elecText.getTextRemark());
		}
	}
}

