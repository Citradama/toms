package com.sds.toms.model;

public class Vbookcategory {

	private Long mcategorypk;
    private String category;
    private Integer totalbook;
    
	public Long getMcategorypk() {
		return mcategorypk;
	}
	public void setMcategorypk(Long mcategorypk) {
		this.mcategorypk = mcategorypk;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getTotalbook() {
		return totalbook;
	}
	public void setTotalbook(Integer totalbook) {
		this.totalbook = totalbook;
	}
    
    
}
