package com.sds.toms.pojo;

public class QuestAnswerModel {

	private Long id;
	private String answerno;
	private String answertext;
	private String isright;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAnswerno() {
		return answerno;
	}
	public void setAnswerno(String answerno) {
		this.answerno = answerno;
	}
	public String getAnswertext() {
		return answertext;
	}
	public void setAnswertext(String answertext) {
		this.answertext = answertext;
	}
	public String getIsright() {
		return isright;
	}
	public void setIsright(String isright) {
		this.isright = isright;
	}
}
