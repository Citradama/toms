package com.sds.toms.viewmodel.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import com.sds.toms.model.Mpaymethod;
import com.sds.toms.util.AppData;

public class CheckoutPaymentVm {

	@Wire
	private Div divMethod;
	@Wire
	private Window winPaymethod;
	
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

				Div divCard = new Div();
				divCard.setClass("card");
				
				Div divCardBody = new Div();
				divCardBody.setClass("card-body");
				
				for(Mpaymethod data : objList) {
					Div divRow = new Div();
					divRow.setClass("row");
					divRow.setStyle("cursor:hand;cursor:pointer");
					divRow.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							Map<String, Object> map = new HashMap<>();
							map.put("paymethod", data);
							Event closeEvent = new Event("onClose", winPaymethod, map);
							Events.postEvent(closeEvent);
						}
					});
					
					Div divCol = new Div();
					divCol.setClass("col-10");
					Label label = new Label(data.getPaymethod());
					divCol.appendChild(label);
					divRow.appendChild(divCol);
					
					divCol = new Div();
					divCol.setClass("col-2");
					divCol.setAlign("right");
					
					Image image = new Image();
					image.setSrc("/images/arrow.png");
					image.setWidth("20px");
					divCol.appendChild(image);
					
					divRow.appendChild(divCol);
					
					Separator separator = new Separator();
					divRow.appendChild(separator);
					
					HtmlNativeComponent hr = new HtmlNativeComponent("hr");
					divRow.appendChild(hr);
					
					divCardBody.appendChild(divRow);
					divCard.appendChild(divCardBody);
					divMethod.appendChild(divCard);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
