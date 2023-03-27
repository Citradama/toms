package com.sds.toms.pojo;

import java.util.List;

public class QuestApprovalReq {

	private String status;
	private String reason;
	private List<Long> questids;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Long> getQuestids() {
		return questids;
	}
	public void setQuestids(List<Long> questids) {
		this.questids = questids;
	}
}
