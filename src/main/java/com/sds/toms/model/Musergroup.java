package com.sds.toms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Musergroup {
	private Integer id;
	private String usergroupcode;
	private String usergroupname;
	private String usergroupdesc;
	private String rolecode;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private Date createtime;

	public Integer getId() {
		return id;
	}

	public String getUsergroupcode() {
		return usergroupcode;
	}

	public String getUsergroupname() {
		return usergroupname;
	}

	public String getUsergroupdesc() {
		return usergroupdesc;
	}

	public String getRolecode() {
		return rolecode;
	}

	public String getCreatedby() {
		return createdby;
	}


    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	public Date getLastupdated() {
		return lastupdated;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUsergroupcode(String usergroupcode) {
		this.usergroupcode = usergroupcode;
	}

	public void setUsergroupname(String usergroupname) {
		this.usergroupname = usergroupname;
	}

	public void setUsergroupdesc(String usergroupdesc) {
		this.usergroupdesc = usergroupdesc;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
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


    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
