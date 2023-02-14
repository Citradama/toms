package com.sds.toms.pojo;

import java.util.List;

import com.sds.toms.model.Tquest;
import com.sds.toms.model.Tquestanswer;

public class BanksoalReq {
	private Tquest data;
	private List<Tquestanswer> dataList;

	public Tquest getData() {
		return data;
	}

	public List<Tquestanswer> getDataList() {
		return dataList;
	}

	public void setData(Tquest data) {
		this.data = data;
	}

	public void setDataList(List<Tquestanswer> dataList) {
		this.dataList = dataList;
	}

}
