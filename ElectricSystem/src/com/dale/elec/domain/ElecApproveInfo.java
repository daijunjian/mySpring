package com.dale.elec.domain;

import java.util.Date;

public class ElecApproveInfo {

	private long approveID;
	private long applicationID;
	private String comment;
	private boolean approval;
	private String approveUserName;
	private Date approveTime;

	private String approveUserID;

	public String getApproveUserID() {
		return approveUserID;
	}

	public void setApproveUserID(String approveUserID) {
		this.approveUserID = approveUserID;
	}

	public long getApproveID() {
		return approveID;
	}

	public void setApproveID(long approveID) {
		this.approveID = approveID;
	}

	public long getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(long applicationID) {
		this.applicationID = applicationID;
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


	public String getApproveUserName() {
		return approveUserName;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public Date getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}

}
