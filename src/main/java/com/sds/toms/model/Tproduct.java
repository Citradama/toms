package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tproduct {
	private Integer id;
	private String productid;
	private String productname;
	private String productdesc;
	private Integer totalquest;
	private Integer totalbook;
	private Integer duration;
	private Integer passingscore;
	private BigDecimal price;
	private String productimgid;
	private String productimgname;
	private String productimglink;
	private Integer productimgsize;
	private String buildtype;
	private String status;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Mcategory category;

	public Integer getId() {
		return id;
	}

	public String getProductid() {
		return productid;
	}

	public String getProductname() {
		return productname;
	}

	public String getProductdesc() {
		return productdesc;
	}

	public Integer getTotalquest() {
		return totalquest;
	}

	public Integer getTotalbook() {
		return totalbook;
	}

	public Integer getDuration() {
		return duration;
	}

	public Integer getPassingscore() {
		return passingscore;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public String getProductimgid() {
		return productimgid;
	}

	public String getProductimgname() {
		return productimgname;
	}

	public String getProductimglink() {
		return productimglink;
	}

	public Integer getProductimgsize() {
		return productimgsize;
	}

	public String getBuildtype() {
		return buildtype;
	}

	public String getStatus() {
		return status;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public String getCreatedby() {
		return createdby;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public Mcategory getCategory() {
		return category;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}

	public void setTotalquest(Integer totalquest) {
		this.totalquest = totalquest;
	}

	public void setTotalbook(Integer totalbook) {
		this.totalbook = totalbook;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public void setPassingscore(Integer passingscore) {
		this.passingscore = passingscore;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setProductimgid(String productimgid) {
		this.productimgid = productimgid;
	}

	public void setProductimgname(String productimgname) {
		this.productimgname = productimgname;
	}

	public void setProductimglink(String productimglink) {
		this.productimglink = productimglink;
	}

	public void setProductimgsize(Integer productimgsize) {
		this.productimgsize = productimgsize;
	}

	public void setBuildtype(String buildtype) {
		this.buildtype = buildtype;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}

	public void setCategory(Mcategory category) {
		this.category = category;
	}

}
