package com.dale.elec.dao.impl;

import org.springframework.stereotype.Repository;

import com.dale.elec.dao.IElecApplicationTemplateDao;
import com.dale.elec.domain.ElecApplicationTemplate;


@Repository(value=IElecApplicationTemplateDao.SERVER_NAME)
public class ElecApplicationTemplateDaoImpl extends CommonDaoImpl<ElecApplicationTemplate> implements IElecApplicationTemplateDao{

	
}
