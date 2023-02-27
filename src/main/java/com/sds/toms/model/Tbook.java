package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tbook {
	private Long id;
	private String bookid;
	private String bookname;
	private String bookdesc;
	private String booklink;
	private BigDecimal fee;
	private Integer productcount;
    private Integer paidcount;
	private Long categoryid;
	private String category;
	private String dosenid;
	private String dosenname;
	private Date createtime;
	private String createdby;
    private Date lastupdated;
    private String updatedby;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Long getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDosenid() {
		return dosenid;
	}
	public void setDosenid(String dosenid) {
		this.dosenid = dosenid;
	}
	public String getDosenname() {
		return dosenname;
	}
	public void setDosenname(String dosenname) {
		this.dosenname = dosenname;
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
    
    

}
