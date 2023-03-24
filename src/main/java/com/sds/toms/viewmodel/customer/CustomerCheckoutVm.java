package com.sds.toms.viewmodel.customer;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import com.sds.toms.model.Mcust;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tproduct;

public class CustomerCheckoutVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	private Mcust oCust;
	
	private Tproduct obj;
	private String harga;
	
	private Div divContent;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tproduct obj,
			@ExecutionArgParam("content") Div divContent) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		oCust = (Mcust) zkSession.getAttribute("oCust");

		this.obj = obj;
		this.divContent = divContent;
		harga = DecimalFormat.getInstance().format(obj.getPrice());
	}
	
	@Command
	@NotifyChange("*")
	public void doSelectPayment() {
		try {
			Window win = (Window) Executions.createComponents("/view/customer/checkoutpayment.zul", null, null);
			win.setClosable(true);
			win.doModal();
			win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getData() != null) {
						
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tproduct getObj() {
		return obj;
	}

	public void setObj(Tproduct obj) {
		this.obj = obj;
	}

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}
}
