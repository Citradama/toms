package com.sds.toms.viewmodel.dosen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.BanksoalReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.pojo.QuestSummary;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class DashboardDosenVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	
	private QuestSummary obj;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		
		doGetsummary();
	}
	
	public void doGetsummary() {
		try {
			ObjectResp Resp = null;

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tquest()
					+ "/statusgroup/dosen/" + oUser.getUserid();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				obj = mapper.convertValue(Resp.getData(), new TypeReference<QuestSummary>() {
				});

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange("*")
	public void doView(@BindingParam("arg") String arg) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSummary", "Y"); 
			
			String url = "";
			if(arg.equals("approve"))
				url = "/view/quest/questwaitapproval.zul";
			else if(arg.equals("decline"))
				url = "/view/quest/questdecline.zul";
			else if(arg.equals("draft"))
				url = "/view/quest/draftquestlist.zul";
			else url = "/view/bank/banksoallist.zul";
			
			Window win = (Window) Executions.createComponents(url, null, map);
			win.setWidth("80%");
			win.setClosable(true);
			win.doModal();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QuestSummary getObj() {
		return obj;
	}

	public void setObj(QuestSummary obj) {
		this.obj = obj;
	}
	

}
