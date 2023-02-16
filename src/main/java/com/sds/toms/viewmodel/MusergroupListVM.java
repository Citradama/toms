package com.sds.toms.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Musergroup;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class MusergroupListVM {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Integer totalrecord;
	private Muser oUser;

	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		doReset();

		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<Musergroup>() {

				@Override
				public void render(Row row, Musergroup data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren().add(new Label(data.getUsergroupcode() != null ? data.getUsergroupcode() : ""));
					row.getChildren().add(new Label(data.getUsergroupname() != null ? data.getUsergroupname() : ""));
					row.getChildren().add(new Label(data.getUsergroupdesc() != null ? data.getUsergroupdesc() : ""));

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
							Window win = (Window) Executions.createComponents("/view/admin/usergroupform.zul", null,
									map);
							win.setWidth("60%");
							win.setClosable(true);
							win.doModal();
							win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

								@Override

								public void onEvent(Event event) throws Exception {
									doReset();
									BindUtils.postNotifyChange(null, null, MusergroupListVM.this, "*");
								}
							});
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
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("obj", data);
							map.put("isEdit", "Y");
							Window win = (Window) Executions.createComponents("/view/admin/usergroupform.zul", null,
									map);
							win.setWidth("60%");
							win.setClosable(true);
							win.doModal();
							win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

								@Override
								public void onEvent(Event event) throws Exception {
									doReset();
									BindUtils.postNotifyChange(null, null, MusergroupListVM.this, "*");
								}
							});
						}

					});

					Button btnDelete = new Button();
					btnDelete.setClass("btn btn-sm btn-danger");
					btnDelete.setIconSclass("z-icon-trash");
					btnDelete.setStyle("border-radius:200px; margin:3px");
					btnDelete.setTooltiptext("Hapus");
					btnDelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@SuppressWarnings({ "unchecked", "rawtypes" })
						@Override
						public void onEvent(Event event) throws Exception {
							try {
								Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
										Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

											public void onEvent(Event event) throws Exception {
												if (event.getName().equals("onOK")) {
													try {
														String url = ConfigUtil.getConfig().getUrl_base()
																+ ConfigUtil.getConfig().getEndpoint_musergroup() + "/"
																+ data.getId();
														System.out.println(url);
														data.setLastupdated(null);
														data.setCreatetime(null);
														ObjectMapper mapper = new ObjectMapper();
														ObjectResp rsp = RespHandler.responObj(url,
																mapper.writeValueAsString(data), AppUtil.METHOD_DEL,
																oUser);

														if (rsp.getCode() == 200) {
															Messagebox.show(Labels.getLabel("common.delete.success"),
																	WebApps.getCurrent().getAppName(), Messagebox.OK,
																	Messagebox.INFORMATION);
														}
														doReset();
														BindUtils.postNotifyChange(null, null, MusergroupListVM.this,
																"*");
													} catch (Exception e) {
														e.printStackTrace();
													}
												}
											}
										});
							} catch (Exception e) {
								e.printStackTrace();
							}
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
		try {

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_musergroup();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				List<Musergroup> objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Musergroup>>() {
				});

				System.out.println(objList.size());
				grid.setModel(new ListModelList<>(objList));
				totalrecord = objList.size();
			} else {
				System.out.println("nulll");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Command
	@NotifyChange("*")
	public void doAddnew() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			Window win = (Window) Executions.createComponents("/view/admin/usergroupform.zul", null, map);
			win.setWidth("60%");
			win.setClosable(true);
			win.doModal();
			win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					doReset();
					BindUtils.postNotifyChange(null, null, MusergroupListVM.this, "*");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(Integer totalrecord) {
		this.totalrecord = totalrecord;
	}
}
