package com.dale.elec.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;

import com.dale.elec.dao.IElecCommonMsgDao;
import com.dale.elec.dao.IElecSystemDDLDao;
import com.dale.elec.domain.ElecSystemDDL;
import com.dale.elec.service.IElecSystemDDLService;
import com.dale.elec.web.form.ElecSystemDDLForm;

@Service(value=IElecSystemDDLService.SREVER_NAME)
public class ElecSystemDDLServiceImpl implements IElecSystemDDLService {
	
	@Resource(name=IElecSystemDDLDao.SERVER_NAME)
	private IElecSystemDDLDao elecSystemDDLDao;

	public List<ElecSystemDDLForm> findKeyWord(){
		List<Object> list = elecSystemDDLDao.findKeyWord();
		List<ElecSystemDDLForm> formList = this.elecSystemDDLObjecListtToVOList(list);
		return formList;
	}
	
	/**值转换，object转换成vo对象*/
	private List<ElecSystemDDLForm> elecSystemDDLObjecListtToVOList(List<Object> list) {
		List<ElecSystemDDLForm> formList = new ArrayList<ElecSystemDDLForm>();
		for(int i=0;list!=null && i<list.size();i++){
			Object object = list.get(i);
			ElecSystemDDLForm elecSystemDDLForm = new ElecSystemDDLForm();
			elecSystemDDLForm.setKeyword(object.toString());
			formList.add(elecSystemDDLForm);
		}
		return formList;
	}

	public List<ElecSystemDDLForm> findSystemDDLListByKeyword(String keyword) {
		List<ElecSystemDDL> list = this.findListByKeyword(keyword);
		List<ElecSystemDDLForm> formList = this.elecSystemPOListToVOList(list);
		return formList;
	}
	
	private List<ElecSystemDDLForm> elecSystemPOListToVOList(List<ElecSystemDDL> list) {
		List<ElecSystemDDLForm> formList = new ArrayList<ElecSystemDDLForm>();
		for(int i=0;list!=null&&i<list.size();i++){
			ElecSystemDDLForm elecSystemDDLForm = new ElecSystemDDLForm();
			ElecSystemDDL elecSystemDDL = list.get(i);
			try {
				BeanUtils.copyProperties(elecSystemDDLForm, elecSystemDDL);
			} catch (Exception e) {
				e.printStackTrace();
			}
			formList.add(elecSystemDDLForm);
		}
		return formList;
	}


	private List<ElecSystemDDL> findListByKeyword(String keyword) {
		//查询条件
		String hqlWhere = " and o.keyword = ?";
		Object [] params = {keyword};
		//排序
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("o.ddlCode", "asc");
		List<ElecSystemDDL> list = elecSystemDDLDao.findCollectionByConditionNoPage(hqlWhere, params, orderby);
		return list;
	}
	
	public void saveSystemDDL(ElecSystemDDLForm elecSystemDDLForm) {
		//获取保存的参数
		String keyword = elecSystemDDLForm.getKeywordname();
		String typeflag = elecSystemDDLForm.getTypeflag();
		//System.err.println(keyword);
		//System.err.println(typeflag);
		String[] itemnames = elecSystemDDLForm.getItemname();
		if(typeflag!=null && typeflag.equals("new")){
			System.err.println("1");
			for (int i = 0; itemnames != null && i < itemnames.length; i++) {
				ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
				elecSystemDDL.setKeyword(keyword);
				
				elecSystemDDL.setDdlCode(i+1);
				elecSystemDDL.setDdlName(itemnames[i]);
				elecSystemDDLDao.save(elecSystemDDL);
			}
		}
		
		//在原有的数据上进行修改
		else{
			System.err.println("2");
			List<ElecSystemDDL> list = this.findListByKeyword(keyword);
			elecSystemDDLDao.deleteObjectsByCollection(list);
			this.saveSystemDDLByCondition(keyword,itemnames);
		}
		
	}

	private void saveSystemDDLByCondition(String keyword, String[] itemnames) {
		if(itemnames!=null){
			//System.err.println("3");
			int len = itemnames.length;
			for (int i = 0; i < itemnames.length; i++) {
				ElecSystemDDL elecSystemDDL = new ElecSystemDDL();
				elecSystemDDL.setKeyword(keyword);
				elecSystemDDL.setDdlCode(i+1);
				elecSystemDDL.setDdlName(itemnames[i]);
				elecSystemDDLDao.save(elecSystemDDL);
			}
		}
		
	}

}
