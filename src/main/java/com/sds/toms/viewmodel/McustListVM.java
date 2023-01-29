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
import com.sds.toms.model.Mcust;
import com.sds.toms.model.Muniversity;
import com.sds.toms.pojo.ObjectResp;
import com.sds.utils.config.ConfigUtil;

public class McustListVM {
	

	private Integer totalrecord;
	
	@Wire
	private Grid grid;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		
		doReset();
		
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Mcust>() {

				@Override
				public void render(Row row, Mcust data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren().add(new Label(data.getCustid() != null ? data.getCustid() : ""));
					row.getChildren().add(new Label(data.getCustname() != null ? data.getCustname() : ""));
					row.getChildren().add(new Label(data.getEmail() != null ? data.getEmail() : ""));

					Button btnDetail = new Button();
					btnDetail.setClass("btn btn-sm btn-info");
					btnDetail.setIconSclass("z-icon-eye");
					btnDetail.setStyle("border-radius:200px; margin:3px");
					btnDetail.setTooltiptext("Detail");
					btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
						}

					});
					
					Button btnEdit = new Button();
					btnEdit.setClass("btn btn-sm btn-success");
					btnEdit.setIconSclass("z-icon-edit");
					btnEdit.setStyle("border-radius:200px; margin:3px");
					btnEdit.setTooltiptext("Ubah");
					btnEdit.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
						}

					});
					
					Button btnDelete = new Button();
					btnDelete.setClass("btn btn-sm btn-danger");
					btnDelete.setIconSclass("z-icon-trash");
					btnDelete.setStyle("border-radius:200px; margin:3px");
					btnDelete.setTooltiptext("Hapus");
					btnDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
						}

					});

					Div div = new Div();
					div.appendChild(btnDetail);
					div.appendChild(btnEdit);
					div.appendChild(btnDelete);
					row.getChildren().add(div);

				}

			});
		}
		
	}
	
	public void doReset() {
		totalrecord = 0;
		
		ObjectResp Resp = null;

		String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mcust();
		Resp = RespHandler.getObject(url);

		if (Resp.getCode() == 200) {
			ObjectMapper mapper = new ObjectMapper();
			List<Mcust> objList = mapper.convertValue(Resp.getData(),
					new TypeReference<List<Mcust>>() {
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
