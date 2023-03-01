package com.sds.toms.viewmodel;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import com.sds.toms.model.Muser;
import com.sds.toms.model.Tproduct;

public class ProductFormVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private Tproduct objForm;
	private boolean isInsert;

	@Wire
	private Window winProduct;
	@Wire
	private Div divFooter;
	@Wire
	private Combobox cbUsergroup;


	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("objForm") Tproduct objForm,
			@ExecutionArgParam("isEdit") String isEdit, @ExecutionArgParam("isDetail") String isDetail) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

	}

	public Muser getoUser() {
		return oUser;
	}

	public Tproduct getObjForm() {
		return objForm;
	}

	public void setoUser(Muser oUser) {
		this.oUser = oUser;
	}

	public void setObjForm(Tproduct objForm) {
		this.objForm = objForm;
	}
}
