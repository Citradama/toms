package com.sds.toms.model;

public class Twishlist {

	private Long id;
    private String custid;
    private String custname;
    private Tproduct product;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCustid() {
		return custid;
	}
	public void setCustid(String custid) {
		this.custid = custid;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public Tproduct getProduct() {
		return product;
	}
	public void setProduct(Tproduct product) {
		this.product = product;
	}

}
