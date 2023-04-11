package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Tproduct {
	private Long id;
	private String productid;
	private String productname;
	private Long categoryid;
	private String categoryname;
	private String iswishlist;
	private String productdesc;
	private Integer totalquest;
	private Integer totalbook;
	private Integer duration;
	private Integer passingscore;
	private BigDecimal price;
	private String productimglink;
	private String buildtype;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date createtime;
	private String createdby;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private Date lastupdated;
	private String updatedby;
	
	public List<String> questids;
	public List<String> bookids;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategoryname() {
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	public String getIswishlist() {
		return iswishlist;
	}
	public void setIswishlist(String iswishlist) {
		this.iswishlist = iswishlist;
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
	public String getProductimglink() {
		return productimglink;
	}
	public void setProductimglink(String productimglink) {
		this.productimglink = productimglink;
	}
	public String getBuildtype() {
		return buildtype;
	}
	public void setBuildtype(String buildtype) {
		this.buildtype = buildtype;
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
	public List<String> getQuestids() {
		return questids;
	}
	public void setQuestids(List<String> questids) {
		this.questids = questids;
	}
	public List<String> getBookids() {
		return bookids;
	}
	public void setBookids(List<String> bookids) {
		this.bookids = bookids;
	}


}
