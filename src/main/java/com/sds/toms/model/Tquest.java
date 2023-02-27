package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tquest {
	private Long id;
	private String questid;
	private String questtext;
	private String questimgid;
	private String questimgname;
	private String questimglink;
	private String rightanswer;
	private BigDecimal fee;
	private String status;
	private Integer productcount;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Mcategory mcategory;
	private Mdosen mdosen;


	public String getQuestid() {
		return questid;
	}

	public void setQuestid(String questid) {
		this.questid = questid;
	}

	public String getQuesttext() {
		return questtext;
	}

	public void setQuesttext(String questtext) {
		this.questtext = questtext;
	}

	public String getQuestimgid() {
		return questimgid;
	}

	public void setQuestimgid(String questimgid) {
		this.questimgid = questimgid;
	}

	public String getQuestimgname() {
		return questimgname;
	}

	public void setQuestimgname(String questimgname) {
		this.questimgname = questimgname;
	}

	public String getQuestimglink() {
		return questimglink;
	}

	public void setQuestimglink(String questimglink) {
		this.questimglink = questimglink;
	}

	public String getRightanswer() {
		return rightanswer;
	}

	public void setRightanswer(String rightanswer) {
		this.rightanswer = rightanswer;
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

	public Mdosen getMdosen() {
		return mdosen;
	}

	public void setMdosen(Mdosen mdosen) {
		this.mdosen = mdosen;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
