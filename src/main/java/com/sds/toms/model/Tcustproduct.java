package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tcustproduct {
	private Integer tcustproductpk;
	private BigDecimal price;
	private Date paidtime;
	private Mcust mcust;
	private Tproduct tproduct;

	public Integer getTcustproductpk() {
		return tcustproductpk;
	}

	public void setTcustproductpk(Integer tcustproductpk) {
		this.tcustproductpk = tcustproductpk;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getPaidtime() {
		return paidtime;
	}

	public void setPaidtime(Date paidtime) {
		this.paidtime = paidtime;
	}

	public Mcust getMcust() {
		return mcust;
	}

	public void setMcust(Mcust mcust) {
		this.mcust = mcust;
	}

	public Tproduct getTproduct() {
		return tproduct;
	}

	public void setTproduct(Tproduct tproduct) {
		this.tproduct = tproduct;
	}

}
