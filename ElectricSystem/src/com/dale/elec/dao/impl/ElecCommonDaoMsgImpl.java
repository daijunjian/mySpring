package com.dale.elec.dao.impl;

import org.springframework.stereotype.Repository;

import com.dale.elec.dao.IElecCommonMsgDao;
import com.dale.elec.domain.ElecCommonMsg;

@Repository(value=IElecCommonMsgDao.SERVER_NAME)
public class ElecCommonDaoMsgImpl extends CommonDaoImpl<ElecCommonMsg> implements IElecCommonMsgDao{

	
	
}
