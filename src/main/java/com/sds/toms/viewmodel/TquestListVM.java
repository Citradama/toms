package com.sds.toms.viewmodel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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
import com.sds.toms.model.Vquestcategory;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class TquestListVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	
	private Integer totalrecord;

	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		doReset();

		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Vquestcategory>() {

				@Override
				public void render(Row row, Vquestcategory data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren()
							.add(new Label(data.getCategory() != null ? data.getCategory() : ""));
					row.getChildren().add(new Label(data.getTotalquest() != null ? NumberFormat.getInstance().format(data.getTotalquest()) : "0"));

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
	
	@NotifyChange("*")
	public void doSearch() {
		try {
			ObjectResp Resp = null;
			List<Vquestcategory> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tquest() + "/categorygroup/dosen/" + oUser.getUserid();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Vquestcategory>>() {
				});

				grid.setModel(new ListModelList<>(objList));
				totalrecord = objList.size();
			} else {
				System.out.println("nulll");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doReset() {
		try {
			totalrecord = 0;
			doSearch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Command
	public void doAddnew() {
		Window win = (Window) Executions.createComponents("/view/bank/banksoalform.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				doReset();
				BindUtils.postNotifyChange(null, null, TquestListVM.this, "*");
			}
		});
	}

	public Integer getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(Integer totalrecord) {
		this.totalrecord = totalrecord;
	}

}
