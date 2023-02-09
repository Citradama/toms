package com.sds.toms.pojo;

import com.sds.toms.model.Mdosen;

public class DosenReq {
	private Mdosen mdosen;
	private String password;
	private String usergroupcode;

	public Mdosen getMdosen() {
		return mdosen;
	}

	public void setMdosen(Mdosen mdosen) {
		this.mdosen = mdosen;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsergroupcode() {
		return usergroupcode;
	}

	public void setUsergroupcode(String usergroupcode) {
		this.usergroupcode = usergroupcode;
	}

}
