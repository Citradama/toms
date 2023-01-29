package com.sds.toms.pojo;

public class ObjectPagingResp {

	private int code;
	private String message;
	private Object data;
	private int pageno;
	private int pagesize;
	private int totalpages;
	private Long recordsize;	
	
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
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}	
	public int getPageno() {
		return pageno;
	}
	public void setPageno(int pageno) {
		this.pageno = pageno;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}	
	public int getTotalpages() {
		return totalpages;
	}
	public void setTotalpages(int totalpages) {
		this.totalpages = totalpages;
	}
	public Long getRecordsize() {
		return recordsize;
	}
	public void setRecordsize(Long recordsize) {
		this.recordsize = recordsize;
	}	
	
}
