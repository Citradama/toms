package com.sds.toms.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Musergroup;
import com.sds.toms.model.Musergroupmenu;
import com.sds.toms.pojo.ObjectResp;
import com.sds.utils.config.ConfigUtil;

public class UserInitializationVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	@Wire
	private Div divContent;
	@Wire
	private Div divAccord;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		try {
			oUser = (Muser) zkSession.getAttribute("oUser");
			doRenderMenu();
			Executions.createComponents("/view/dashboard.zul", divContent, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doRenderMenu() {
		try {
			String menugroup = "";

			Div divAccordItem = null;
			Div divAccordBody = null;
			int idx = 0;
			int idxParent = 0;

			ObjectResp Resp = null;

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_musergroupmenu()
					+ "/usergroup/1";
			Resp = RespHandler.getObject(url);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				List<Musergroupmenu> oList = mapper.convertValue(Resp.getData(),
						new TypeReference<List<Musergroupmenu>>() {
						});
				
				for (final Musergroupmenu obj : oList) {
					if (!menugroup.equals(obj.getMmenu().getMenugroup())) {
						idx++;

						divAccordItem = new Div();
						divAccordItem.setSclass("accordion-item");
						divAccordItem.setParent(divAccord);

						HtmlNativeComponent h2 = new HtmlNativeComponent("h2");
						h2.setClientAttribute("id", "headingOne" + idx);
						h2.setClientAttribute("class", "accordion-header headingOne" + idx);
						h2.setParent(divAccordItem);

						HtmlNativeComponent buttonHeader = new HtmlNativeComponent("button");
						buttonHeader.setClientAttribute("class", "accordion-button collapsed");
						buttonHeader.setClientAttribute("type", "button");
						buttonHeader.setClientAttribute("data-mdb-toggle", "collapse");
						buttonHeader.setClientAttribute("data-mdb-target", ".collapseOne" + idx);
						buttonHeader.setClientAttribute("aria-expanded", "false");
						buttonHeader.setClientAttribute("aria-controls", "collapseOne" + idx);
						Label label = new Label(obj.getMmenu().getMenugroup());
						label.setStyle("font-size:14px");
						buttonHeader.appendChild(label);
						buttonHeader.setParent(h2);

						HtmlNativeComponent divCollapse = new HtmlNativeComponent("div");
						divCollapse.setClientAttribute("class", "accordion-collapse collapse collapseOne" + idx);
						divCollapse.setClientAttribute("aria-labelledby", ".headingOne" + idx);
						divCollapse.setClientAttribute("data-mdb-parent", ".headingOne" + idx);
						divCollapse.setParent(divAccordItem);

						divAccordBody = new Div();
						divAccordBody.setSclass("accordion-body");
						divAccordBody.setParent(divCollapse);

					}
					menugroup = obj.getMmenu().getMenugroup();

					A a = new A();
					a.setHref("");
					a.setSclass("list-group-item list-group-item-action py-2 ");
					a.setParent(divAccordBody);

					HtmlNativeComponent i = new HtmlNativeComponent("i");
					i.setClientAttribute("class", "fas fa-circle fa-fw me-3");
					HtmlNativeComponent span = new HtmlNativeComponent("span");
					Label label = new Label(obj.getMmenu().getMenuname());
					label.setStyle("font-size:14px");
					span.appendChild(label);
					a.appendChild(i);
					a.appendChild(span);
					a.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							A aPrev = (A) divAccord.getAttribute("active");
							if (aPrev != null)
								aPrev.setSclass("list-group-item list-group-item-action py-2");

							a.setSclass("list-group-item list-group-item-action py-2 active");
							divAccord.setAttribute("active", a);

							Clients.evalJavaScript("hidenavbar()");

							divContent.getChildren().clear();
							Executions.createComponents(obj.getMmenu().getMenupath(), divContent, null);
						}
					});
				}
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doLogout() {
		if (zkSession.getAttribute("oUser") != null) {
			zkSession.removeAttribute("oUser");
		}
		Executions.sendRedirect("/logout.zul");
	}

}
