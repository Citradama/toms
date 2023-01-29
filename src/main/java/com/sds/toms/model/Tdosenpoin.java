package com.sds.toms.model;

import java.math.BigDecimal;
import java.util.Date;

public class Tdosenpoin {
	private Integer tdosenpoinpk;
	private Integer totalquestsold;
	private Integer totalbooksold;
	private BigDecimal totalquestamount;
	private BigDecimal totalbookamount;
	private BigDecimal totalamount;
	private BigDecimal totaldisburse;
	private Date lastupdated;
	private Mdosen mdosen;

	public Integer getTdosenpoinpk() {
		return tdosenpoinpk;
	}

	public void setTdosenpoinpk(Integer tdosenpoinpk) {
		this.tdosenpoinpk = tdosenpoinpk;
	}

	public Integer getTotalquestsold() {
		return totalquestsold;
	}

	public void setTotalquestsold(Integer totalquestsold) {
		this.totalquestsold = totalquestsold;
	}

	public Integer getTotalbooksold() {
		return totalbooksold;
	}

	public void setTotalbooksold(Integer totalbooksold) {
		this.totalbooksold = totalbooksold;
	}

	public BigDecimal getTotalquestamount() {
		return totalquestamount;
	}

	public void setTotalquestamount(BigDecimal totalquestamount) {
		this.totalquestamount = totalquestamount;
	}

	public BigDecimal getTotalbookamount() {
		return totalbookamount;
	}

	public void setTotalbookamount(BigDecimal totalbookamount) {
		this.totalbookamount = totalbookamount;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public BigDecimal getTotaldisburse() {
		return totaldisburse;
	}

	public void setTotaldisburse(BigDecimal totaldisburse) {
		this.totaldisburse = totaldisburse;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Mdosen getMdosen() {
		return mdosen;
	}

	public void setMdosen(Mdosen mdosen) {
		this.mdosen = mdosen;
	}

}
