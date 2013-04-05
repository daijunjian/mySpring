package com.dale.elec.web.form;

import java.io.File;
import java.io.InputStream;

public class ElecApplicationFlowForm {

	private File upload;// 用于文件上传

	private String applicationTemplateName;// 申请模板名称

	private String applicationID; // 主键ID
	private String applicationTemplateID; // 申请模板表的主键
	private String title; // 上传的文件标题
	private String path; // 上传的文件的存储路径
	private String applicationUserID; // 申请人ID
	private String applicationLogonName; // 申请人登录名
	private String applicationUserName; // 申请人姓名
	private String applyTime; // 申请日期
	private String status; // 当前审批状态

	private String approveID; // 主键ID
	private String comment; // 审批意见
	private boolean approval; // 审批结果，是否通过（同意或者不同意）
	private String approveUserID; // 审批人ID
	private String approveUserName; // 审批人姓名
	private String approveTime; // 审批日期

	private InputStream inputStream;

	private String taskID;// 任务ID，用来获取当前任务所指定的连线

	private String outcome;// 指定的连线

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getTaskID() {
		return taskID;
	}

	public void setTaskID(String taskID) {
		this.taskID = taskID;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getApplicationTemplateName() {
		return applicationTemplateName;
	}

	public void setApplicationTemplateName(String applicationTemplateName) {
		this.applicationTemplateName = applicationTemplateName;
	}

	public String getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	public String getApplicationTemplateID() {
		return applicationTemplateID;
	}

	public void setApplicationTemplateID(String applicationTemplateID) {
		this.applicationTemplateID = applicationTemplateID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getApplicationUserID() {
		return applicationUserID;
	}

	public void setApplicationUserID(String applicationUserID) {
		this.applicationUserID = applicationUserID;
	}

	public String getApplicationLogonName() {
		return applicationLogonName;
	}

	public void setApplicationLogonName(String applicationLogonName) {
		this.applicationLogonName = applicationLogonName;
	}

	public String getApplicationUserName() {
		return applicationUserName;
	}

	public void setApplicationUserName(String applicationUserName) {
		this.applicationUserName = applicationUserName;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApproveID() {
		return approveID;
	}

	public void setApproveID(String approveID) {
		this.approveID = approveID;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getApproveUserID() {
		return approveUserID;
	}

	public void setApproveUserID(String approveUserID) {
		this.approveUserID = approveUserID;
	}

	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

}
