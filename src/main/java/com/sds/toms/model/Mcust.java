package com.sds.toms.model;

import java.util.Date;

public class Mcust {
	private Integer id;
	private String custid;
	private String custname;
	private String major;
	private String hp;
	private String email;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Muniversity university;

	public Integer getId() {
		return id;
	}

	public String getCustid() {
		return custid;
	}

	public String getCustname() {
		return custname;
	}

	public String getMajor() {
		return major;
	}

	public String getHp() {
		return hp;
	}

	public String getEmail() {
		return email;
	}

	public String getRowstat() {
		return rowstat;
	}

	public Integer getRowedit() {
		return rowedit;
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

	public Muniversity getUniversity() {
		return university;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCustid(String custid) {
		this.custid = custid;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRowstat(String rowstat) {
		this.rowstat = rowstat;
	}

	public void setRowedit(Integer rowedit) {
		this.rowedit = rowedit;
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

	public void setUniversity(Muniversity university) {
		this.university = university;
	}

}
