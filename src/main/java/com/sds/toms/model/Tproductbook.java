package com.sds.toms.model;

import java.util.Date;

public class Tproductbook {
	private Integer tproductbook;
	private String rowstat;
	private Integer rowedit;
	private Date lastupdated;
	private String updatedby;
	private Tproduct tproduct;
	private Tbook tbook;

	public Integer getTproductbook() {
		return tproductbook;
	}

	public void setTproductbook(Integer tproductbook) {
		this.tproductbook = tproductbook;
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

	public Tproduct getTproduct() {
		return tproduct;
	}

	public void setTproduct(Tproduct tproduct) {
		this.tproduct = tproduct;
	}

	public Tbook getTbook() {
		return tbook;
	}

	public void setTbook(Tbook tbook) {
		this.tbook = tbook;
	}

}
