package com.dale.elec.web.form;

import java.io.File;
import java.util.Date;

public class ElecUserForm {

	private String userID;
	private String jctID; // 所属单位id
	private String userName;
	private String logonName;
	private String logonPwd;
	private String sexID;
	private String birthday;
	private String address;
	private String contactTel;
	private String email;
	private String mobile;
	private String isDuty;
	private String onDutyDate;
	private String offDutyDate;
	private String remark;

	private File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 用于判断跳转的页面 * 当viewflag==1的时候，说明当前跳转的是查看明细的页面 *
	 * 当viewflag==null的时候，，说明当前跳转的是编辑的页面
	 */
	private String viewflag;

	/**
	 * 用于判断当前操作是否对密码进行了修改 * 如果对密码没有进行修改，设置md5flag=1，在密码进行保存的时候，不需要对密码进行md5的加密 *
	 * 当md5falg=null，说明在密码进行保存的时候，需要对密码进行md5的加密（新增，修改了密码）
	 */
	private String md5flag;

	private String flag;

	/**
	 * roleflag用来判断当前操作时系统管理员和高级管理员点击编辑，roleflag=null，保存时要重定向到userIndex.jsp
	 * 还是高级管理员之下的角色点击编辑，此时roleflag=1，保存时要在跳转到编辑页面userEdit.jsp
	 */
	private String roleflag;

	public String getRoleflag() {
		return roleflag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getViewflag() {
		return viewflag;
	}

	public void setViewflag(String viewflag) {
		this.viewflag = viewflag;
	}

	public String getMd5flag() {
		return md5flag;
	}

	public void setMd5flag(String md5flag) {
		this.md5flag = md5flag;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getJctID() {
		return jctID;
	}

	public void setJctID(String jctID) {
		this.jctID = jctID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogonName() {
		return logonName;
	}

	public void setLogonName(String logonName) {
		this.logonName = logonName;
	}

	public String getLogonPwd() {
		return logonPwd;
	}

	public void setLogonPwd(String logonPwd) {
		this.logonPwd = logonPwd;
	}

	public String getSexID() {
		return sexID;
	}

	public void setSexID(String sexID) {
		this.sexID = sexID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactTel() {
		return contactTel;
	}

	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getIsDuty() {
		return isDuty;
	}

	public void setIsDuty(String isDuty) {
		this.isDuty = isDuty;
	}

	public String getOnDutyDate() {
		return onDutyDate;
	}

	public void setOnDutyDate(String onDutyDate) {
		this.onDutyDate = onDutyDate;
	}

	public String getOffDutyDate() {
		return offDutyDate;
	}

	public void setOffDutyDate(String offDutyDate) {
		this.offDutyDate = offDutyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
