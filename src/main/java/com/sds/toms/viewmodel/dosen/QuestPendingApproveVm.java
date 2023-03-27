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
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabs;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcategory;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.BanksoalReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.pojo.QuestApprovalReq;
import com.sds.toms.util.AppData;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class QuestPendingApproveVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private QuestApprovalReq objReq;
	private List<Long> questids = new ArrayList<>();
	private Map<Long, BanksoalReq> map = new HashMap<>();

	private Integer totalrecord;
	private String reason;
	private Long categoryid;

	@Wire
	private Grid grid;
	@Wire
	private Tabs tabs;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

		doRendertabs();
		doReset();
		if (grid != null) {
			grid.setRowRenderer(new RowRenderer<BanksoalReq>() {

				@Override
				public void render(Row row, BanksoalReq data, int index) throws Exception {
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
					row.getChildren().add(new Label(data.getQuestid()));
					row.getChildren().add(new Label(data.getCategory()));
					row.getChildren().add(new Label(data.getQuesttext()));
					row.getChildren()
							.add(new Label(new SimpleDateFormat("dd-MM-yyyy HH:mm").format(data.getCreatetime())));
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

	public void doRendertabs() {
		try {
			List<Mcategory> categories = AppData.getMcategory();
			for (Mcategory data : categories) {
				if (categoryid == null)
					categoryid = data.getId();
				Tab tab = new Tab(data.getCategory());
				tab.addEventListener(Events.ON_SELECT, new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {
						doSearch(data.getId());
						categoryid = data.getId();

						BindUtils.postNotifyChange(null, null, QuestPendingApproveVm.class, "categoryid");
					}
				});
				tab.setParent(tabs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@NotifyChange("*")
	public void doSearch(Long categoryid) {
		try {
			ObjectResp Resp = null;
			List<BanksoalReq> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tquest()
					+ "/category/" + categoryid;
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<BanksoalReq>>() {
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
		doSearch(categoryid);
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

										for (Entry<Long, BanksoalReq> entry : map.entrySet()) {
											BanksoalReq obj = entry.getValue();
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
