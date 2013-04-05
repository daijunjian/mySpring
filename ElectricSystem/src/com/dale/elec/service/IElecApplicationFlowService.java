package com.dale.elec.service;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dale.elec.web.form.ElecApplicationFlowForm;

public interface IElecApplicationFlowService {

	public final static String SERVER_MAME = "com.dale.elec.service.impl.ElecApplicationFlowServiceImpl";

	void saveApplication(ElecApplicationFlowForm elecApplicationFlowForm, HttpServletRequest request);

	List<ElecApplicationFlowForm> findApplicationList(
			ElecApplicationFlowForm elecApplicationFlowForm, HttpServletRequest request);

	List<ElecApplicationFlowForm> findMyTaskWithApplicationList(HttpServletRequest request);

	Collection<String> findOutcomdList(ElecApplicationFlowForm elecApplicationFlowForm);

	InputStream downloadAttach(ElecApplicationFlowForm elecApplicationFlowForm);

	void approve(ElecApplicationFlowForm elecApplicationFlowForm, HttpServletRequest request);

	List<ElecApplicationFlowForm> findFlowApprovedHidstory(
			ElecApplicationFlowForm elecApplicationFlowForm);
}
