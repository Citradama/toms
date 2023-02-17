package com.sds.toms.viewmodel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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
import org.zkoss.zk.ui.util.Clients;
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
import com.sds.toms.model.Mdosen;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class MdosenListVM {

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
			grid.setRowRenderer(new RowRenderer<Mdosen>() {

				@Override
				public void render(Row row, Mdosen data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren().add(new Label(data.getDosenid() != null ? data.getDosenid() : ""));
					row.getChildren().add(new Label(data.getDosenname() != null ? data.getDosenname() : ""));
					row.getChildren().add(
							new Label(data.getUniversity() != null ? data.getUniversity().getUniversityname() : ""));

					Button btnDetail = new Button();
					btnDetail.setClass("btn btn-sm btn-info");
					btnDetail.setIconSclass("z-icon-eye");
					btnDetail.setStyle("border-radius:200px; margin:3px");
					btnDetail.setTooltiptext("Detail");
					btnDetail.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("objForm", data);
							map.put("isDetail", "Y");
							Window win = (Window) Executions.createComponents("/view/master/lect/lecturerform.zul",
									null, map);
							win.setWidth("60%");
							win.setClosable(true);
							win.doModal();
							win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

								@Override

								public void onEvent(Event event) throws Exception {
									doReset();
									BindUtils.postNotifyChange(null, null, MdosenListVM.this, "*");
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
							map.put("objForm", data);
							map.put("isEdit", "Y");
							Window win = (Window) Executions.createComponents("/view/master/lect/lecturerform.zul",
									null, map);
							win.setWidth("60%");
							win.setClosable(true);
							win.doModal();
							win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

								@Override

								public void onEvent(Event event) throws Exception {
									doReset();
									BindUtils.postNotifyChange(null, null, MdosenListVM.this, "*");
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
																+ ConfigUtil.getConfig().getEndpoint_mdosen() + "/"
																+ data.getId();
														System.out.println(url);
														data.setLastupdated(null);
														data.setCreatetime(null);
														ObjectMapper mapper = new ObjectMapper();
														ObjectResp rsp = RespHandler.responObj(url,
																mapper.writeValueAsString(data), AppUtil.METHOD_DEL,
																oUser);

														if (rsp.getCode() == 200) {
															Clients.evalJavaScript(
																	"swal.fire({" + "icon: 'success',\r\n"
																			+ "  title: 'Berhasil',\r\n" + "  text: '"
																			+ Labels.getLabel("common.delete.success")
																			+ "'," + "})");
														}
														doReset();
														BindUtils.postNotifyChange(null, null, MdosenListVM.this, "*");
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

	@Command
	public void doAddnew() {
		Window win = (Window) Executions.createComponents("/view/master/lect/lecturerform.zul", null, null);
		win.setClosable(true);
		win.doModal();
		win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				doReset();
				BindUtils.postNotifyChange(null, null, MdosenListVM.this, "*");
			}
		});
	}

	public void doReset() {
		totalrecord = 0;

		ObjectResp Resp = null;

		String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mdosen();
		try {
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				List<Mdosen> objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Mdosen>>() {
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

	public Integer getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(Integer totalrecord) {
		this.totalrecord = totalrecord;
	}
}
