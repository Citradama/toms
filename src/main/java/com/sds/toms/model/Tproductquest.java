package com.sds.toms.model;

import java.util.Date;

public class Tproductquest {
	private Integer tproductquestpk;
	private String rowstat;
	private Integer rowedit;
	private Date lastupdated;
	private String updatedby;
	private Tproduct tproduct;
	private Tquest tquest;

	public Integer getTproductquestpk() {
		return tproductquestpk;
	}

	public void setTproductquestpk(Integer tproductquestpk) {
		this.tproductquestpk = tproductquestpk;
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

	public Tquest getTquest() {
		return tquest;
	}

	public void setTquest(Tquest tquest) {
		this.tquest = tquest;
	}

}
