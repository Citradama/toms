package com.sds.toms.viewmodel.dosen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.BanksoalReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class QuestWaitApprovalVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	
	private Integer totalrecord;
	
	@Wire
	private Grid grid;
	@Wire
	private Caption caption;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("isSummary") String isSummary) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		
		if(isSummary != null && isSummary.equals("Y"))
			caption.setVisible(true);
		
		doReset();
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<BanksoalReq>() {

				@Override
				public void render(Row row, BanksoalReq data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren().add(new Label(data.getQuestid()));
					row.getChildren().add(new Label(data.getCategory()));
					row.getChildren().add(new Label(data.getQuesttext()));
					row.getChildren().add(new Label(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(data.getCreatetime())));
					
					Button btnDetail = new Button();
					btnDetail.setClass("btn btn-sm btn-info");
					btnDetail.setIconSclass("z-icon-eye");
					btnDetail.setStyle("border-radius:200px; margin:3px");
					btnDetail.setTooltiptext("Detail");
					btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("obj", data);
							map.put("isDetail", "Y"); 
							Window win = (Window) Executions.createComponents("/view/bank/banksoalform.zul", null, map);
							win.setWidth("60%");
							win.setClosable(true);
							win.doModal();
						}

					});
					
					Div div = new Div();
					div.appendChild(btnDetail);
					row.getChildren().add(div);
				}
			});
		}
		
	}
	
	@NotifyChange("*")
	public void doSearch() {
		try {
			ObjectResp Resp = null;
			List<BanksoalReq> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tquest()
					+ "/waitapproval/dosen/" + oUser.getUserid();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<BanksoalReq>>() {
				});

				if(objList == null)
					objList = new ArrayList<>();
				
				grid.setModel(new ListModelList<>(objList));
				totalrecord = objList.size();
			} else {
				System.out.println("null");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@NotifyChange("*")
	public void doReset() {
		totalrecord = 0;
		doSearch();
	}

	public Integer getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(Integer totalrecord) {
		this.totalrecord = totalrecord;
	}

}
