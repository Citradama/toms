package com.sds.toms.pojo;

import java.util.Date;
import java.util.List;

public class QuestModel {

	private List<QuestAnswerModel> answers;
	private String category;
	private Integer categoryid;
	private String createdby;
	private Date createtime;
	private String dosenid;
	private String dosenname;
	private Long id;
	private Integer paidcount;
	private String questid;
	private String questtext;
	private String rightanswer;

	public List<QuestAnswerModel> getAnswers() {
		return answers;
	}

	public String getCategory() {
		return category;
	}

	public Integer getCategoryid() {
		return categoryid;
	}

	public String getCreatedby() {
		return createdby;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public String getDosenid() {
		return dosenid;
	}

	public String getDosenname() {
		return dosenname;
	}

	public Long getId() {
		return id;
	}

	public Integer getPaidcount() {
		return paidcount;
	}

	public String getQuestid() {
		return questid;
	}

	public String getQuesttext() {
		return questtext;
	}

	public String getRightanswer() {
		return rightanswer;
	}

	public void setAnswers(List<QuestAnswerModel> answers) {
		this.answers = answers;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCategoryid(Integer categoryid) {
		this.categoryid = categoryid;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public void setDosenid(String dosenid) {
		this.dosenid = dosenid;
	}

	public void setDosenname(String dosenname) {
		this.dosenname = dosenname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPaidcount(Integer paidcount) {
		this.paidcount = paidcount;
	}

	public void setQuestid(String questid) {
		this.questid = questid;
	}

	public void setQuesttext(String questtext) {
		this.questtext = questtext;
	}

	public void setRightanswer(String rightanswer) {
		this.rightanswer = rightanswer;
	}

}
