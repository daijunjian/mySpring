package com.dale.elec.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ElecSystemDDL implements Serializable{

	
	private Integer seqID;
	private String keyword;
	private Integer ddlCode;
	private String ddlName;

	public Integer getSeqID() {
		return seqID;
	}

	public void setSeqID(Integer seqID) {
		this.seqID = seqID;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getDdlCode() {
		return ddlCode;
	}

	public void setDdlCode(Integer ddlCode) {
		this.ddlCode = ddlCode;
	}

	public String getDdlName() {
		return ddlName;
	}

	public void setDdlName(String ddlName) {
		this.ddlName = ddlName;
	}

}
