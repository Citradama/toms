package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tbook {
	private Integer tbookpk;
	private String bookid;
	private String bookname;
	private String bookdesc;
	private String bookfilename;
	private Integer bookfilesize;
	private String booklink;
	private BigDecimal fee;
	private String status;
	private Integer productcount;
	private Integer paidcount;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Mdosen mdosen;

	public Integer getTbookpk() {
		return tbookpk;
	}

	public void setTbookpk(Integer tbookpk) {
		this.tbookpk = tbookpk;
	}

	public String getBookid() {
		return bookid;
	}

	public void setBookid(String bookid) {
		this.bookid = bookid;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getBookdesc() {
		return bookdesc;
	}

	public void setBookdesc(String bookdesc) {
		this.bookdesc = bookdesc;
	}

	public String getBookfilename() {
		return bookfilename;
	}

	public void setBookfilename(String bookfilename) {
		this.bookfilename = bookfilename;
	}

	public Integer getBookfilesize() {
		return bookfilesize;
	}

	public void setBookfilesize(Integer bookfilesize) {
		this.bookfilesize = bookfilesize;
	}

	public String getBooklink() {
		return booklink;
	}

	public void setBooklink(String booklink) {
		this.booklink = booklink;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProductcount() {
		return productcount;
	}

	public void setProductcount(Integer productcount) {
		this.productcount = productcount;
	}

	public Integer getPaidcount() {
		return paidcount;
	}

	public void setPaidcount(Integer paidcount) {
		this.paidcount = paidcount;
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
