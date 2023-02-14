package com.sds.toms.pojo;

public class SearchReq {

	private String general;
	private String status;
	private Integer musergroupfk;
	private Integer mcategoryfk;
	private Integer mdosenfk;

	public String getGeneral() {
		return general;
	}

	public String getStatus() {
		return status;
	}

	public Integer getMusergroupfk() {
		return musergroupfk;
	}

	public Integer getMcategoryfk() {
		return mcategoryfk;
	}

	public Integer getMdosenfk() {
		return mdosenfk;
	}

	public void setGeneral(String general) {
		this.general = general;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setMusergroupfk(Integer musergroupfk) {
		this.musergroupfk = musergroupfk;
	}

	public void setMcategoryfk(Integer mcategoryfk) {
		this.mcategoryfk = mcategoryfk;
	}

	public void setMdosenfk(Integer mdosenfk) {
		this.mdosenfk = mdosenfk;
	}

}
