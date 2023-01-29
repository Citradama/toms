package com.sds.toms.model;

import java.util.Date;

public class Tquestanswer {
	private Integer tquestanswerpk;
	private String answerno;
	private String answertext;
	private String answerimgid;
	private String answerimgname;
	private String answerimglink;
	private String isright;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Tquest tquest;
	public Integer getTquestanswerpk() {
		return tquestanswerpk;
	}
	public void setTquestanswerpk(Integer tquestanswerpk) {
		this.tquestanswerpk = tquestanswerpk;
	}
	public String getAnswerno() {
		return answerno;
	}
	public void setAnswerno(String answerno) {
		this.answerno = answerno;
	}
	public String getAnswertext() {
		return answertext;
	}
	public void setAnswertext(String answertext) {
		this.answertext = answertext;
	}
	public String getAnswerimgid() {
		return answerimgid;
	}
	public void setAnswerimgid(String answerimgid) {
		this.answerimgid = answerimgid;
	}
	public String getAnswerimgname() {
		return answerimgname;
	}
	public void setAnswerimgname(String answerimgname) {
		this.answerimgname = answerimgname;
	}
	public String getAnswerimglink() {
		return answerimglink;
	}
	public void setAnswerimglink(String answerimglink) {
		this.answerimglink = answerimglink;
	}
	public String getIsright() {
		return isright;
	}
	public void setIsright(String isright) {
		this.isright = isright;
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
	public Tquest getTquest() {
		return tquest;
	}
	public void setTquest(Tquest tquest) {
		this.tquest = tquest;
	}
	
}
