package com.sds.toms.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BanksoalReq {
	private Long id;
	private String questid;
	private String questtext;
	private String questimglink;
	private String rightanswer;
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
	private List<QuestAnswerModel> answers;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
	public List<QuestAnswerModel> getAnswers() {
		return answers;
	}
	public void setAnswers(List<QuestAnswerModel> answers) {
		this.answers = answers;
	}

	
}
