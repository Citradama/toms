package com.sds.toms.pojo;

import com.sds.toms.model.Mcust;

public class CustReq {
	private Mcust mcust;
	private String password;
	private String usergroupcode;

	public Mcust getMcust() {
		return mcust;
	}

	public String getPassword() {
		return password;
	}

	public String getUsergroupcode() {
		return usergroupcode;
	}

	public void setMcust(Mcust mcust) {
		this.mcust = mcust;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsergroupcode(String usergroupcode) {
		this.usergroupcode = usergroupcode;
	}
}
