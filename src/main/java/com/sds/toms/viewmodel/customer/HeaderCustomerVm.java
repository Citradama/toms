package com.sds.toms.viewmodel.customer;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;

import com.sds.toms.model.Muser;

public class HeaderCustomerVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Map<String, Object> map = new HashMap<>();

	private String username;
	private String searchcover;

	@Wire
	private Div divContent;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		if (oUser != null) {
			username = "Hi, " + oUser.getUsername();
			searchcover = "";
		}

		map = new HashMap<String, Object>();
		map.put("content", divContent);
		Executions.createComponents("/view/homecustomer.zul", divContent, map);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSearchcover() {
		return searchcover;
	}

	public void setSearchcover(String searchcover) {
		this.searchcover = searchcover;
	}
}
