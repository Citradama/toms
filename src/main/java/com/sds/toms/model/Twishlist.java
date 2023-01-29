package com.sds.toms.model;

import java.util.Date;

public class Twishlist {
	private Integer twishlistpk;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Mcust mcust;
	private Tproduct tproduct;

	public Integer getTwishlistpk() {
		return twishlistpk;
	}

	public void setTwishlistpk(Integer twishlistpk) {
		this.twishlistpk = twishlistpk;
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

	public Mcust getMcust() {
		return mcust;
	}

	public void setMcust(Mcust mcust) {
		this.mcust = mcust;
	}

	public Tproduct getTproduct() {
		return tproduct;
	}

	public void setTproduct(Tproduct tproduct) {
		this.tproduct = tproduct;
	}

}
