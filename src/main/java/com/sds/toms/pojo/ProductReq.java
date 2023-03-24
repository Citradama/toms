package com.sds.toms.pojo;

import java.math.BigDecimal;
import java.util.List;

public class ProductReq {
private Integer id;
private String productid;
private String productname;
private String categoryname;
private int categoryid;
private String productdesc;
private int totalquest;
private int totalbook;
private int duration;
private int passingscore;
private BigDecimal price;
private String buildtype;
private List<String> questids;
private List<String> bookids;

public Integer getId() {
	return id;
}

public String getProductid() {
	return productid;
}

public String getProductname() {
	return productname;
}

public String getCategoryname() {
	return categoryname;
}

public int getCategoryid() {
	return categoryid;
}

public String getProductdesc() {
	return productdesc;
}

public int getTotalquest() {
	return totalquest;
}

public int getTotalbook() {
	return totalbook;
}

public int getDuration() {
	return duration;
}

public int getPassingscore() {
	return passingscore;
}

public BigDecimal getPrice() {
	return price;
}

public String getBuildtype() {
	return buildtype;
}

public void setId(Integer id) {
	this.id = id;
}

public void setProductid(String productid) {
	this.productid = productid;
}

public void setProductname(String productname) {
	this.productname = productname;
}

public void setCategoryname(String categoryname) {
	this.categoryname = categoryname;
}

public void setCategoryid(int categoryid) {
	this.categoryid = categoryid;
}

public void setProductdesc(String productdesc) {
	this.productdesc = productdesc;
}

public void setTotalquest(int totalquest) {
	this.totalquest = totalquest;
}

public void setTotalbook(int totalbook) {
	this.totalbook = totalbook;
}

public void setDuration(int duration) {
	this.duration = duration;
}

public void setPassingscore(int passingscore) {
	this.passingscore = passingscore;
}

public void setPrice(BigDecimal price) {
	this.price = price;
}

public void setBuildtype(String buildtype) {
	this.buildtype = buildtype;
}

public List<String> getQuestids() {
	return questids;
}

public List<String> getBookids() {
	return bookids;
}

public void setQuestids(List<String> questids) {
	this.questids = questids;
}

public void setBookids(List<String> bookids) {
	this.bookids = bookids;
}

}
