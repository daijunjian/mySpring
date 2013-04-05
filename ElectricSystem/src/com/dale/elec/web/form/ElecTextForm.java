package com.dale.elec.web.form;

import java.util.Date;

/**
 * 
 * vo对象，称作值对象（对应页面中表单的属性） po对象和vo对象的相同点：都是javaBean对象
 * po对象和vo对象不同点：数据库表发生变化，po对象同时修改 vo对象对应页面表单的属性，当表单属性发生变化时，vo也变化
 */
public class ElecTextForm {

	private String textID;
	private String textName;
	private String textDate;
	private String textRemark;

	public String getTextID() {
		return textID;
	}

	public void setTextID(String textID) {
		this.textID = textID;
	}

	public String getTextName() {
		return textName;
	}

	public void setTextName(String textName) {
		this.textName = textName;
	}

	public String getTextDate() {
		return textDate;
	}

	public void setTextDate(String textDate) {
		this.textDate = textDate;
	}

	public String getTextRemark() {
		return textRemark;
	}

	public void setTextRemark(String textRemark) {
		this.textRemark = textRemark;
	}

}
