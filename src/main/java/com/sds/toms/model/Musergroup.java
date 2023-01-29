package com.sds.toms.model;

import java.util.Date;

public class Musergroup {
	private Integer musergrouppk;
	private String usergroupcode;
	private String usergroupname;
	private String usergroupdesc;
	private String rolecode;
	private String rowstat;
	private Integer rowedit;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;

	public Integer getMusergrouppk() {
		return musergrouppk;
	}

	public void setMusergrouppk(Integer musergrouppk) {
		this.musergrouppk = musergrouppk;
	}

	public String getUsergroupcode() {
		return usergroupcode;
	}

	public void setUsergroupcode(String usergroupcode) {
		this.usergroupcode = usergroupcode;
	}

	public String getUsergroupname() {
		return usergroupname;
	}

	public void setUsergroupname(String usergroupname) {
		this.usergroupname = usergroupname;
	}

	public String getUsergroupdesc() {
		return usergroupdesc;
	}

	public void setUsergroupdesc(String usergroupdesc) {
		this.usergroupdesc = usergroupdesc;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
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

}
