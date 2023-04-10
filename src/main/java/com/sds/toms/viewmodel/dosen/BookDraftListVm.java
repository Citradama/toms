package com.sds.toms.viewmodel.dosen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Checkbox;
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
import com.sds.toms.model.Tbook;
import com.sds.toms.pojo.BanksoalReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class BookDraftListVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Map<Long, Tbook> map = new HashMap<>();
	private Integer totalrecord;
	private Integer totalselected;

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
			grid.setRowRenderer(new RowRenderer<Tbook>() {

				@Override
				public void render(Row row, Tbook data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));

					Checkbox check = new Checkbox();
					check.setAttribute("obj", data);
					check.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
						@Override
						public void onEvent(Event event) throws Exception {
							Checkbox checked = (Checkbox) event.getTarget();
							if (checked.isChecked()) {
								map.put(data.getId(), data);
							} else {
								map.remove(data.getId());
							}
							totalselected = map.size();
							BindUtils.postNotifyChange(null, null, BookDraftListVm.this, "totalselected");
						}
					});
					if (map.get(data.getId()) != null)
						check.setChecked(true);
					row.getChildren().add(check);
					row.getChildren().add(new Label(data.getBookid()));
					row.getChildren().add(new Label(data.getCategory()));
					row.getChildren().add(new Label(data.getBookname()));
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
							Window win = (Window) Executions.createComponents("/view/bank/bankmateriform.zul", null, map);
							win.setWidth("60%");
							win.setClosable(true);
							win.doModal();
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
							Window win = (Window) Executions.createComponents("/view/bank/bankmateriform.zul", null, map);
							win.setWidth("60%");
							win.setClosable(true);
							win.doModal();
							win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

								@Override

								public void onEvent(Event event) throws Exception {
									doReset();
									BindUtils.postNotifyChange(null, null, BookDraftListVm.this, "*");
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
							Messagebox.show(Labels.getLabel("common.delete.confirm"), "Confirm Dialog",
									Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

										public void onEvent(Event event) throws Exception {
											if (event.getName().equals("onOK")) {
												try {
													String url = ConfigUtil.getConfig().getUrl_base()
															+ ConfigUtil.getConfig().getEndpoint_tbook() + "/"
															+ data.getId();
													ObjectMapper mapper = new ObjectMapper();
													data.setLastupdated(null);
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
													BindUtils.postNotifyChange(null, null, BookDraftListVm.this, "*");
												} catch (Exception e) {
													e.printStackTrace();
												}
											}
										}
							});
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
	@NotifyChange("totalselected")
	public void doCheckedall(@BindingParam("checked") Boolean checked) {
		try {
			map = new HashMap<>();
			List<Row> components = grid.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(1);
				Tbook obj = (Tbook) chk.getAttribute("obj");
				if (checked) {
					chk.setChecked(true);
					map.put(obj.getId(), obj);
				} else {
					chk.setChecked(false);
					map.remove(obj.getId());
				}
			}
			totalselected = map.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doSearch() {
		try {
			ObjectResp Resp = null;
			List<Tbook> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tbook()
					+ "/draft/dosen/" + oUser.getUserid();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Tbook>>() {
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
	
	@Command
	@NotifyChange("*")
	public void doSave() {
		if(map.size() > 0) {
			try {
				String url = "";
				ObjectResp rsp = null;
				ObjectMapper mapper = new ObjectMapper();
				
				List<Tbook> objList = new ArrayList<>();
				for(Entry<Long, Tbook> data : map.entrySet()) {
					Tbook obj = data.getValue();
					objList.add(obj);
				}
				url = ConfigUtil.getConfig().getUrl_base()
						+ ConfigUtil.getConfig().getEndpoint_tbook() + "/submitdraft";
				System.out.println("update : " + url);

				rsp = new ObjectResp();
				rsp = RespHandler.responObj(url, mapper.writeValueAsString(objList),
						AppUtil.METHOD_PUT, oUser);

				doReset();
				if (rsp.getCode() == 200) {
					Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
							+ "  title: 'Berhasil',\r\n" + "  text: '"
							+ Labels.getLabel("common.update.success") + "'," + "})");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Clients.evalJavaScript(
					"swal.fire({" + "icon: 'info',\r\n" + "  title: 'Informasi',\r\n"
							+ "  text: 'Silahkan pilih soal terlebih dahulu.'," + "})");
		}
		
	}

	@NotifyChange("*")
	public void doReset() {
		try {
			map = new HashMap<>();
			totalrecord = 0;
			totalselected = 0;
			doSearch();
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

	public Integer getTotalselected() {
		return totalselected;
	}

	public void setTotalselected(Integer totalselected) {
		this.totalselected = totalselected;
	}

}
