package com.dale.elec.service;

import com.dale.elec.web.form.ElecCommonMsgForm;

public interface IElecCommonMsgService {

	final static String SERVER_NAME = "com.dale.elec.service.impl.ElecCommonMsgService";

	ElecCommonMsgForm findElecCommonMsg();

	void saveElecCommonMsg(ElecCommonMsgForm elecCommonMsgForm);
}
