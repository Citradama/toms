package com.sds.toms.viewmodel.customer;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

public class IndexCustVm {
	
	private Map<String, Object> map = new HashMap<>();

	@Wire
	private Div divMateri;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		map = new HashMap<String, Object>();
		map.put("content", divMateri);
		Executions.createComponents("indexcust.zul", divMateri, map);
	}
}
