package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tdisburse {

	private Integer tdisbursepk;
	private BigDecimal amount;
	private Date disbursedate;
	private String status;
	private String potfilename;
	private Integer potfilesize;
	private String potfilelink;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Mdosen mdosen;

	public Integer getTdisbursepk() {
		return tdisbursepk;
	}

	public void setTdisbursepk(Integer tdisbursepk) {
		this.tdisbursepk = tdisbursepk;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDisbursedate() {
		return disbursedate;
	}

	public void setDisbursedate(Date disbursedate) {
		this.disbursedate = disbursedate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPotfilename() {
		return potfilename;
	}

	public void setPotfilename(String potfilename) {
		this.potfilename = potfilename;
	}

	public Integer getPotfilesize() {
		return potfilesize;
	}

	public void setPotfilesize(Integer potfilesize) {
		this.potfilesize = potfilesize;
	}

	public String getPotfilelink() {
		return potfilelink;
	}

	public void setPotfilelink(String potfilelink) {
		this.potfilelink = potfilelink;
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

	public Mdosen getMdosen() {
		return mdosen;
	}

	public void setMdosen(Mdosen mdosen) {
		this.mdosen = mdosen;
	}

}
