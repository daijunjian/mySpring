package com.dale.elec.dao.impl;
import org.springframework.stereotype.Repository;

import com.dale.elec.dao.IElecApplicationDao;
import com.dale.elec.domain.ElecApplication;

@Repository(value=IElecApplicationDao.SERVER_NAME)
public class ElecApplicationDaoImpl extends CommonDaoImpl<ElecApplication>implements IElecApplicationDao{

	
	
}
