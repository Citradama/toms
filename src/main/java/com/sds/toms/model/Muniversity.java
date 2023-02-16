package com.sds.toms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Muniversity {
	private Integer id;
	private String universityname;
	private Date createtime;
	private String createdby;
	private Date lastupdated;
	private String updatedby;

	public Integer getId() {
		return id;
	}

	public String getUniversityname() {
		return universityname;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public String getCreatedby() {
		return createdby;
	}

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	public Date getLastupdated() {
		return lastupdated;
	}

	public String getUpdatedby() {
		return updatedby;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUniversityname(String universityname) {
		this.universityname = universityname;
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

}
