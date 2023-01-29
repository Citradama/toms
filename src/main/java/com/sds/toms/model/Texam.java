package com.sds.toms.model;

import java.util.Date;

public class Texam {
	private Integer texampk;
	private String examid;
	private Date examstart;
	private Date examfinish;
	private Integer examduration;
	private Integer score;
	private Integer totalright;
	private String ispass;
	private String isdone;
	private Mcust mcust;
	private Tproduct tproduct;

	public Integer getTexampk() {
		return texampk;
	}

	public void setTexampk(Integer texampk) {
		this.texampk = texampk;
	}

	public String getExamid() {
		return examid;
	}

	public void setExamid(String examid) {
		this.examid = examid;
	}

	public Date getExamstart() {
		return examstart;
	}

	public void setExamstart(Date examstart) {
		this.examstart = examstart;
	}

	public Date getExamfinish() {
		return examfinish;
	}

	public void setExamfinish(Date examfinish) {
		this.examfinish = examfinish;
	}

	public Integer getExamduration() {
		return examduration;
	}

	public void setExamduration(Integer examduration) {
		this.examduration = examduration;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getTotalright() {
		return totalright;
	}

	public void setTotalright(Integer totalright) {
		this.totalright = totalright;
	}

	public String getIspass() {
		return ispass;
	}

	public void setIspass(String ispass) {
		this.ispass = ispass;
	}

	public String getIsdone() {
		return isdone;
	}

	public void setIsdone(String isdone) {
		this.isdone = isdone;
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
