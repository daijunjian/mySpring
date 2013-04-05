package com.dale.elec.web.form;

public class ElecSystemDDLForm {

	private String seqID;
	private String keyword;
	private String ddlCode;
	private String ddlName;

	// 添加两个页面隐藏域的值
	private String keywordname;
	private String typeflag;

	private String[] itemname;

	public String getKeywordname() {
		return keywordname;
	}

	public void setKeywordname(String keywordname) {
		this.keywordname = keywordname;
	}

	public String getTypeflag() {
		return typeflag;
	}

	public void setTypeflag(String typeflag) {
		this.typeflag = typeflag;
	}

	public String[] getItemname() {
		return itemname;
	}

	public void setItemname(String[] itemname) {
		this.itemname = itemname;
	}

	public String getSeqID() {
		return seqID;
	}

	public void setSeqID(String seqID) {
		this.seqID = seqID;
	}

	public String getDdlCode() {
		return ddlCode;
	}

	public void setDdlCode(String ddlCode) {
		this.ddlCode = ddlCode;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDdlName() {
		return ddlName;
	}

	public void setDdlName(String ddlName) {
		this.ddlName = ddlName;
	}
}
