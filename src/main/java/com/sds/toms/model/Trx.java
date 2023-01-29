package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Trx {
	private Integer ttrxpk;
	private String trxno;
	private Date trxtime;
	private BigDecimal trxamount;
	private String status;
	private BigDecimal paidamount;
	private Date paidtime;
	private Mcust mcust;
	private Mpaymethod mpaymethod;
	private Tproduct tproduct;

	public Integer getTtrxpk() {
		return ttrxpk;
	}

	public void setTtrxpk(Integer ttrxpk) {
		this.ttrxpk = ttrxpk;
	}

	public String getTrxno() {
		return trxno;
	}

	public void setTrxno(String trxno) {
		this.trxno = trxno;
	}

	public Date getTrxtime() {
		return trxtime;
	}

	public void setTrxtime(Date trxtime) {
		this.trxtime = trxtime;
	}

	public BigDecimal getTrxamount() {
		return trxamount;
	}

	public void setTrxamount(BigDecimal trxamount) {
		this.trxamount = trxamount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getPaidamount() {
		return paidamount;
	}

	public void setPaidamount(BigDecimal paidamount) {
		this.paidamount = paidamount;
	}

	public Date getPaidtime() {
		return paidtime;
	}

	public void setPaidtime(Date paidtime) {
		this.paidtime = paidtime;
	}

	public Mcust getMcust() {
		return mcust;
	}

	public void setMcust(Mcust mcust) {
		this.mcust = mcust;
	}

	public Mpaymethod getMpaymethod() {
		return mpaymethod;
	}

	public void setMpaymethod(Mpaymethod mpaymethod) {
		this.mpaymethod = mpaymethod;
	}

	public Tproduct getTproduct() {
		return tproduct;
	}

	public void setTproduct(Tproduct tproduct) {
		this.tproduct = tproduct;
	}

}
