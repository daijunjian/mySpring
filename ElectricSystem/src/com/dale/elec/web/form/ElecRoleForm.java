package com.dale.elec.web.form;

public class ElecRoleForm {

	private String roleID;
	// 权限code的集合
	private String[] selectoper;
	// 用户的集合
	private String[] selectuser;

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String[] getSelectoper() {
		return selectoper;
	}

	public void setSelectoper(String[] selectoper) {
		this.selectoper = selectoper;
	}

	public String[] getSelectuser() {
		return selectuser;
	}

	public void setSelectuser(String[] selectuser) {
		this.selectuser = selectuser;
	}

}
