package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tproduct {
	private Integer tproductpk;
	private String productid;
	private String productname;
	private String productdesc;
	private Integer totalquest;
	private Integer totalbook;
	private Integer duration;
	private Integer passingscore;
	private BigDecimal price;
	private String status;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Mcategory mcategory;

	public Integer getTproductpk() {
		return tproductpk;
	}

	public void setTproductpk(Integer tproductpk) {
		this.tproductpk = tproductpk;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public String getProductdesc() {
		return productdesc;
	}

	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}

	public Integer getTotalquest() {
		return totalquest;
	}

	public void setTotalquest(Integer totalquest) {
		this.totalquest = totalquest;
	}

	public Integer getTotalbook() {
		return totalbook;
	}

	public void setTotalbook(Integer totalbook) {
		this.totalbook = totalbook;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getPassingscore() {
		return passingscore;
	}

	public void setPassingscore(Integer passingscore) {
		this.passingscore = passingscore;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Mcategory getMcategory() {
		return mcategory;
	}

	public void setMcategory(Mcategory mcategory) {
		this.mcategory = mcategory;
	}

}
