package com.dale.elec.domain;

import java.util.Date;

public class ElecApplication {

	public static String APP_NOCHECK = "1";//未审核
	public static String APP_RUNNING = "2";//审核中
	public static String APP_PASS = "3";//审核通过
	public static String APP_REJUCT = "4";//审核不通过
	
	private long applicationID;
	private long applicationTemplateID;
	private String title;
	private String path;
	private String applicationUserID;
	private String applicationLogonName;
	private String applicationUserName;
	private Date applyTime;
	private String status;

	public long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(long applicationID) {
		this.applicationID = applicationID;
	}

	public long getApplicationTemplateID() {
		return applicationTemplateID;
	}

	public void setApplicationTemplateID(long applicationTemplateID) {
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

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	
}
