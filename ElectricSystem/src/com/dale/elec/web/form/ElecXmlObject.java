package com.dale.elec.web.form;

public class ElecXmlObject {

	private String code;      //权限code
	private String name;      //权限名称
	private String parentCode;//父级权限的code
	private String parentName;//父级权限的名称

	/**
	 * 用来判断当前角色具有的功能权限在所有的功能权限中是否匹配到
	 *    * 如果flag=1：表示当前角色具有该权限，则页面复选框被选中
          * 如果flag=2：表示当前角色不具有该权限，则页面复选框不被选中
	 * */
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	
}
