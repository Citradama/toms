package com.sds.toms.pojo;

import com.sds.toms.model.Muser;

public class LoginResp {
	private int code;
	private String message;
	private Muser data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Muser getData() {
		return data;
	}

	public void setData(Muser data) {
		this.data = data;
	}

}
