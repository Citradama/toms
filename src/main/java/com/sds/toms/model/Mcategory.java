package com.sds.toms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Mcategory {

	private Integer id;
	private String category;
	private Date createtime;
	private String createdby;
	private Date lastupdated;

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

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
