package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tquest {
	private Long id;
	private String questid;
	private String questtext;
	private String questimgid;
	private String questimgname;
	private Long questimgsize;
	private String questimglink;
	private String rightanswer;
	private BigDecimal fee;
	private String status;
	private Integer productcount;
	private Integer paidcount;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Mcategory category;
	private Mdosen dosen;

	public Long getId() {
		return id;
	}

	public String getQuestid() {
		return questid;
	}

	public String getQuesttext() {
		return questtext;
	}

	public String getQuestimgid() {
		return questimgid;
	}

	public String getQuestimgname() {
		return questimgname;
	}

	public Long getQuestimgsize() {
		return questimgsize;
	}

	public String getQuestimglink() {
		return questimglink;
	}

	public String getRightanswer() {
		return rightanswer;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public String getStatus() {
		return status;
	}

	public Integer getProductcount() {
		return productcount;
	}

	public Integer getPaidcount() {
		return paidcount;
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


	public void setId(Long id) {
		this.id = id;
	}

	public void setQuestid(String questid) {
		this.questid = questid;
	}

	public void setQuesttext(String questtext) {
		this.questtext = questtext;
	}

	public void setQuestimgid(String questimgid) {
		this.questimgid = questimgid;
	}

	public void setQuestimgname(String questimgname) {
		this.questimgname = questimgname;
	}

	public void setQuestimgsize(Long questimgsize) {
		this.questimgsize = questimgsize;
	}

	public void setQuestimglink(String questimglink) {
		this.questimglink = questimglink;
	}

	public void setRightanswer(String rightanswer) {
		this.rightanswer = rightanswer;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setProductcount(Integer productcount) {
		this.productcount = productcount;
	}

	public void setPaidcount(Integer paidcount) {
		this.paidcount = paidcount;
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

	public Mcategory getCategory() {
		return category;
	}

	public Mdosen getDosen() {
		return dosen;
	}

	public void setCategory(Mcategory category) {
		this.category = category;
	}

	public void setDosen(Mdosen dosen) {
		this.dosen = dosen;
	}

	

}
