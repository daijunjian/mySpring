package com.dale.elec.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dale.elec.dao.IElecCommonMsgDao;
import com.dale.elec.domain.ElecCommonMsg;
import com.dale.elec.service.IElecCommonMsgService;
import com.dale.elec.web.form.ElecCommonMsgForm;

@Service(IElecCommonMsgService.SERVER_NAME)
@Transactional(readOnly=true)
public class ElecCommonMsgService implements IElecCommonMsgService {
	
	@Resource(name=IElecCommonMsgDao.SERVER_NAME)
	private IElecCommonMsgDao elecCommonMsgDao;
	
	public ElecCommonMsgForm findElecCommonMsg() {
		//System.err.println("2");
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("",null,null);
		ElecCommonMsgForm commonMsgForm = this.ElecCommonMsgPOToVO(list);
		return commonMsgForm;
		
	}

	private ElecCommonMsgForm ElecCommonMsgPOToVO(List<ElecCommonMsg> list) {
		ElecCommonMsgForm commonMsgForm = new ElecCommonMsgForm();
		if(list!=null && list.size()>0){
			ElecCommonMsg commonMsg = list.get(0);
//			commonMsgForm.setComID(commonMsg.getComID());
//			commonMsgForm.setStationRun(commonMsg.getStationRun());
//			commonMsgForm.setDevRun(commonMsg);
//			commonMsgForm.setCreateDate(createDate);
			try {
				BeanUtils.copyProperties(commonMsgForm, commonMsg);
				commonMsgForm.setCreateDate(String.valueOf(commonMsg.getCreateDate()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return commonMsgForm;
	}

	/**  
	* @Parameters: ElecCommonMsgForm elecCommonMsgForm：传递页面需要保存的值
	* @Return: 无
	*/
	@Transactional(isolation=Isolation.DEFAULT,propagation=Propagation.REQUIRED,readOnly=false)
	public void saveElecCommonMsg(ElecCommonMsgForm elecCommonMsgForm) {
		List<ElecCommonMsg> list = elecCommonMsgDao.findCollectionByConditionNoPage("", null, null);
		//判断数据库中是否存在代办事宜的数据
		//数据库中存在数据
		if(list!=null && list.size()>0){
			ElecCommonMsg elecCommonMsg = list.get(0);
			this.elecCommonMsgVOToPO(elecCommonMsg,elecCommonMsgForm);
			//执行更新
			elecCommonMsgDao.update(elecCommonMsg);
		}
		//不存在数据
		else{
			ElecCommonMsg elecCommonMsg = new ElecCommonMsg();
			this.elecCommonMsgVOToPO(elecCommonMsg,elecCommonMsgForm);
			//执行保存
			elecCommonMsgDao.save(elecCommonMsg);
		}
	}

	/**值转换，将VO对象转换成PO对象，用于保存和更新*/
	private void elecCommonMsgVOToPO(ElecCommonMsg elecCommonMsg,ElecCommonMsgForm elecCommonMsgForm) {
		elecCommonMsg.setStationRun(elecCommonMsgForm.getStationRun());
		elecCommonMsg.setDevRun(elecCommonMsgForm.getDevRun());
		elecCommonMsg.setCreateDate(new Date());
	}	
	

}
