package com.dale.elec.dao.impl;

import org.springframework.stereotype.Repository;

import com.dale.elec.dao.IElecApproveInfoDao;
import com.dale.elec.domain.ElecApproveInfo;

@Repository(value=IElecApproveInfoDao.SERVER_NAME)
public class ElecApproveInfoDaoImpl extends CommonDaoImpl<ElecApproveInfo>implements IElecApproveInfoDao{

	
}
