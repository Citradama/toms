package com.sds.toms.model;

import java.util.Date;

public class Muser {
	private Integer muserpk;
	private String userid;
	private String username;
	private String password;
	private Date lastlogin;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Musergroup musergroup;

	public Integer getMuserpk() {
		return muserpk;
	}

	public void setMuserpk(Integer muserpk) {
		this.muserpk = muserpk;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastlogin() {
		return lastlogin;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
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

	public Musergroup getMusergroup() {
		return musergroup;
	}

	public void setMusergroup(Musergroup musergroup) {
		this.musergroup = musergroup;
	}

}
