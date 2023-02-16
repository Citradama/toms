package com.sds.toms.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Muser {
	private Integer id;
	private String userid;
	private String username;
	private Date lastlogin;
	private String createdby;
	private Date lastupdated;
	private String updatedby;
	private String password;
	private Musergroup usergroup;
	private String token;
	private Date createtime;

	public Integer getId() {
		return id;
	}

	public String getUserid() {
		return userid;
	}

	public String getUsername() {
		return username;
	}


    @JsonFormat(pattern="yyyy-MM-dd")
	public Date getLastlogin() {
		return lastlogin;
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

	public Musergroup getUsergroup() {
		return usergroup;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setLastlogin(Date lastlogin) {
		this.lastlogin = lastlogin;
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

	public void setUsergroup(Musergroup usergroup) {
		this.usergroup = usergroup;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
