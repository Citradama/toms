package com.sds.toms.viewmodel;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcategory;
import com.sds.toms.model.Tquest;
import com.sds.toms.pojo.ObjectResp;
import com.sds.utils.config.ConfigUtil;

public class TquestListVM {

	private Integer totalrecord;
	
	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		doReset();

		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Tquest>() {

				@Override
				public void render(Row row, Tquest data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren().add(new Label(data.getQuestid() != null ? data.getQuestid() : ""));
					row.getChildren()
							.add(new Label(data.getMcategory() != null ? data.getMcategory().getCategory() : ""));
					row.getChildren().add(new Label(data.getMdosen() != null ? data.getMdosen().getDosenname() : ""));
					row.getChildren().add(new Label(data.getRowstat() != null ? data.getRowstat() : ""));
					row.getChildren().add(new Label(data.getStatus() != null ? data.getStatus() : ""));

					Button btnDetail = new Button("Detail");
					btnDetail.setClass("btn btn-sm btn-info");
					btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
						}

					});

					Div div = new Div();
					div.setClass("btn-group");
					div.appendChild(btnDetail);
					row.getChildren().add(div);

				}

			});
		}

	}

	public void doReset() {
		totalrecord = 0;
		ObjectResp Resp = null;

		String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mcategory();
		Resp = RespHandler.getObject(url);

		if (Resp.getCode() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			List<Mcategory> objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Mcategory>>() {
			});

			System.out.println(objList.size());
			grid.setModel(new ListModelList<>(objList));
			totalrecord = objList.size();
		} else {
			System.out.println("nulll");
		}
	}

	public Integer getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(Integer totalrecord) {
		this.totalrecord = totalrecord;
	}
}
