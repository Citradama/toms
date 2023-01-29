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
import com.sds.toms.model.Muniversity;
import com.sds.toms.pojo.ObjectResp;
import com.sds.utils.config.ConfigUtil;

public class MuniversityListVM {

	@Wire
	private Grid grid;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		doReset();
		
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Muniversity>() {

				@Override
				public void render(Row row, Muniversity data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren().add(new Label(data.getUniversityname() != null ? data.getUniversityname() : ""));

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
		ObjectResp Resp = null;

		String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_muniversity();
		Resp = RespHandler.getObject(url);

		if (Resp.getCode() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			List<Muniversity> objList = mapper.convertValue(Resp.getData(),
					new TypeReference<List<Muniversity>>() {
					});

			System.out.println(objList.size());
			grid.setModel(new ListModelList<>(objList));
		} else {
			System.out.println("nulll");
		}
	}
	
}
