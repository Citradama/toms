package com.sds.toms.viewmodel.dosen;

import java.util.HashMap;
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
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.BookSummary;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.pojo.QuestSummary;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class DashboardDosenVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	
	private QuestSummary obj;
	private BookSummary objBook;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		
		if(oUser != null)
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
			
			url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tbook()
					+ "/statusgroup/dosen/" + oUser.getUserid();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objBook = mapper.convertValue(Resp.getData(), new TypeReference<BookSummary>() {
				});

			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	@NotifyChange("*")
	public void doViewQuest(@BindingParam("arg") String arg) {
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
	
	@Command
	@NotifyChange("*")
	public void doViewBook(@BindingParam("arg") String arg) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSummary", "Y"); 
			
			String url = "";
			if(arg.equals("approve"))
				url = "/view/book/bookwaitapproval.zul";
			else if(arg.equals("decline"))
				url = "/view/book/bookdecline.zul";
			else if(arg.equals("draft"))
				url = "/view/book/bookdraftlist.zul";
			else url = "/view/bank/bankmaterilist.zul";
			
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

	public BookSummary getObjBook() {
		return objBook;
	}

	public void setObjBook(BookSummary objBook) {
		this.objBook = objBook;
	}
	

}
