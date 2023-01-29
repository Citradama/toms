package com.sds.toms.model;

import java.util.Date;

public class Mdosen {

	private Integer mdosenpk;
	private String dosenid;
	private String dosenname;
	private String title;
	private String position;
	private String nik;
	private String hp;
	private String email;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Muniversity muniversity;

	public Integer getMdosenpk() {
		return mdosenpk;
	}

	public void setMdosenpk(Integer mdosenpk) {
		this.mdosenpk = mdosenpk;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Muniversity getMuniversity() {
		return muniversity;
	}

	public void setMuniversity(Muniversity muniversity) {
		this.muniversity = muniversity;
	}

}
