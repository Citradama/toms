package com.sds.toms.model;

import java.util.Date;

public class Mcategory {

	private Integer mcategorypk;
	private String category;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;

	public Integer getMcategorypk() {
		return mcategorypk;
	}

	public void setMcategorypk(Integer mcategorypk) {
		this.mcategorypk = mcategorypk;
	}

	public String getRowstat() {
		return rowstat;
	}

	public void setRowstat(String rowstat) {
		this.rowstat = rowstat;
	}

	public Integer getRowedit() {
		return rowedit;
	}

	public void setRowedit(Integer rowedit) {
		this.rowedit = rowedit;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
