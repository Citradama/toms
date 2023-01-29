package com.sds.toms.model;

import java.util.Date;

public class Msysparam {
	private String paramcode;
	private String ismasked;
	private Integer orderno;
	private String paramdesc;
	private String paramgroup;
	private Date lastupdated;
	private String paramvalue;
	private String updatedby;

	public String getParamcode() {
		return paramcode;
	}

	public void setParamcode(String paramcode) {
		this.paramcode = paramcode;
	}

	public String getIsmasked() {
		return ismasked;
	}

	public void setIsmasked(String ismasked) {
		this.ismasked = ismasked;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public String getParamdesc() {
		return paramdesc;
	}

	public void setParamdesc(String paramdesc) {
		this.paramdesc = paramdesc;
	}

	public String getParamgroup() {
		return paramgroup;
	}

	public void setParamgroup(String paramgroup) {
		this.paramgroup = paramgroup;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getParamvalue() {
		return paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

}
