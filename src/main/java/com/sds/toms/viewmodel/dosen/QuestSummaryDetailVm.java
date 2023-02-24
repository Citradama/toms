package com.sds.toms.viewmodel.dosen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlNativeComponent;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Vlayout;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Vquestcategory;
import com.sds.toms.pojo.BanksoalReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.pojo.QuestAnswerModel;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class QuestSummaryDetailVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Vquestcategory obj;
	private boolean isSummery;

	@Wire
	private Grid grid;
	@Wire
	private Groupbox groupbox;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("obj") Vquestcategory obj, @ExecutionArgParam("isSummary") String isSummary) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

		if(isSummary != null && isSummary.equals("Y")) {
			this.isSummery = true;
			this.obj = new Vquestcategory();
		} else {
			this.obj = obj;
		}
		
		doRender();
	}

	@SuppressWarnings("deprecation")
	@NotifyChange("*")
	public void doRender() {
		try {
			ObjectResp Resp = null;
			List<BanksoalReq> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tquest()
					+ "/category/" + obj.getMcategorypk() + "/dosen/" + oUser.getUserid();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<BanksoalReq>>() {
				});

				Integer index = 1;
				Rows rows = new Rows();
				for (BanksoalReq data : objList) {
					Row row = new Row();
					Label labelno = new Label(String.valueOf(index++));
					row.appendChild(labelno);

					Vlayout vlayout = new Vlayout();
					if(data.getQuestimglink() != null) {
						Image img = new Image();
						img.setSrc(data.getQuestimglink());
						img.setWidth("30%");
						vlayout.appendChild(img);
					}
					Label label = new Label(data.getQuesttext());
					vlayout.appendChild(label);
					Separator separator = new Separator();
					vlayout.appendChild(separator);
					for (QuestAnswerModel answer : data.getAnswers()) {
						label = new Label(answer.getAnswerno() + ". " + answer.getAnswertext());
						if (answer.getIsright().equals("Y"))
							label.setStyle("font-weight: bold");
						vlayout.appendChild(label);
					}
					Div div = new Div();
					div.setAlign("right");
					div.setStyle("padding-right:40px");
					label = new Label(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(data.getCreatetime()));
					div.appendChild(label);
					vlayout.appendChild(div);
					HtmlNativeComponent hr = new HtmlNativeComponent("hr");
					vlayout.appendChild(hr);
					row.appendChild(vlayout);
					rows.appendChild(row);
				}
				grid.getChildren().add(rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Vquestcategory getObj() {
		return obj;
	}

	public void setObj(Vquestcategory obj) {
		this.obj = obj;
	}

}
