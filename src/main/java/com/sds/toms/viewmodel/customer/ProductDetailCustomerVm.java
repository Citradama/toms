package com.sds.toms.viewmodel.customer;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Div;

import com.sds.toms.model.Muser;
import com.sds.toms.model.Tproduct;

public class ProductDetailCustomerVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Tproduct obj;
	private String username;
	private String searchcover;
	private String harga;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tproduct obj, @ExecutionArgParam("content") Div divContent) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		
		this.obj = obj;
		username = oUser.getUsername();
		searchcover = "";
		harga = DecimalFormat.getInstance().format(obj.getPrice());
	}

	public Tproduct getObj() {
		return obj;
	}

	public void setObj(Tproduct obj) {
		this.obj = obj;
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

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}
}
