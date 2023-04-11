package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tcheckout {

	private Long checkoutid;
	private String custid;
	private String custname;
	private Long categoryid;
	private String category;
	private String productid;
	private String productname;
	private String productdesc;
	private String productimglink;
	private BigDecimal price;
	private Date checktime;
	
	public Long getCheckoutid() {
		return checkoutid;
	}
	public void setCheckoutid(Long checkoutid) {
		this.checkoutid = checkoutid;
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
	public Long getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getProductdesc() {
		return productdesc;
	}
	public void setProductdesc(String productdesc) {
		this.productdesc = productdesc;
	}
	public String getProductimglink() {
		return productimglink;
	}
	public void setProductimglink(String productimglink) {
		this.productimglink = productimglink;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Date getChecktime() {
		return checktime;
	}
	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}
	
}
