package com.sds.toms.viewmodel;

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
import org.zkoss.bind.annotation.NotifyChange;
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
import com.sds.toms.pojo.QuestApprovalReq;
import com.sds.toms.util.AppUtil;
import com.sds.toms.viewmodel.dosen.BookDraftListVm;
import com.sds.utils.config.ConfigUtil;

public class BookPendingApproveVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private QuestApprovalReq objReq;
	private List<Long> questids = new ArrayList<>();
	private Map<Long, Tbook> map = new HashMap<>();

	private Integer totalrecord;
	private String reason;

	@Wire
	private Grid grid;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

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
			List<Tbook> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tbook()
					+ "/waitapproval";
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Tbook>>() {
				});

				if (objList == null)
					objList = new ArrayList<>();

				grid.setModel(new ListModelList<>(objList));
				totalrecord = objList.size();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doReset() {
		totalrecord = 0;
		questids = new ArrayList<>();
		map = new HashMap<>();
		objReq = new QuestApprovalReq();
		reason = "";
		doSearch();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	@NotifyChange("*")
	public void doApprove(@BindingParam("arg") String arg) {
		try {
			Messagebox.show("Apakah anda yakin untuk melanjutkan proses?", "Confirm Dialog",
					Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

						public void onEvent(Event event) throws Exception {
							if (event.getName().equals("onOK")) {
								if (map.size() > 0) {
									if (arg.equals("A") || arg.equals("D") && reason.trim().length() > 0) {
										String url = "";
										ObjectMapper mapper = new ObjectMapper();
										ObjectResp rsp = null;

										for (Entry<Long, Tbook> entry : map.entrySet()) {
											Tbook obj = entry.getValue();
											questids.add(obj.getId());
										}

										objReq.setStatus(arg);
										objReq.setReason(reason);
										objReq.setQuestids(questids);

										url = ConfigUtil.getConfig().getUrl_base()
												+ ConfigUtil.getConfig().getEndpoint_tquest() + "/approval";
										rsp = RespHandler.responObj(url, mapper.writeValueAsString(objReq),
												AppUtil.METHOD_PUT, oUser);
										if (rsp.getCode() == 201 || rsp.getCode() == 200) {
											String label = arg.equals("A") ? "Approve Data Berhasil."
													: "Decline Data Berhasil";
											doReset();
											Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
													+ "  title: 'Berhasil',\r\n" + "  text: '" + label + "'," + "})");
										} else {
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
															+ "  text: 'Data gagal disimpan'," + "})");
										}
									} else {
										Clients.evalJavaScript(
												"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
														+ "  text: 'Silahkan isi catatan terlebih dahulu.'," + "})");
									}
								} else {
									Clients.evalJavaScript(
											"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
													+ "  text: 'Tidak ada data yang dipilih..'," + "})");
								}
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(Integer totalrecord) {
		this.totalrecord = totalrecord;
	}
}
