package com.sds.toms.viewmodel.customer;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

import com.sds.toms.model.Mpaymethod;
import com.sds.toms.util.AppData;

public class CheckoutPaymentVm {

	@Wire
	private Div divMethod;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		doRender();
	}
	
	@SuppressWarnings("deprecation")
	@Command
	public void doRender() {
		try {
			List<Mpaymethod> objList = AppData.getMpaymethod();
			if(objList.size() > 0) {
				for(Mpaymethod data : objList) {
					Div divRow = new Div();
					divRow.setClass("row");
					
					Div divCol = new Div();
					divCol.setClass("col-10");
					Label label = new Label(data.getPaymethod());
					divCol.appendChild(label);
					divRow.appendChild(divCol);
					
					divCol = new Div();
					divCol.setClass("col-2");
					divCol.setAlign("right");
					label = new Label(">");
					divCol.appendChild(label);
					divRow.appendChild(divCol);
					
					HtmlNativeComponent hr = new HtmlNativeComponent("hr");
					divRow.appendChild(hr);
					divMethod.appendChild(divRow);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
