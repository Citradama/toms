package com.sds.toms.viewmodel;

import java.net.ConnectException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.json.JSONObject;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mmenu;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Musergroup;
import com.sds.toms.model.Musergroupmenu;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class MusergroupFormVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	private Musergroup obj;

	private List<Musergroupmenu> listGroupmenu;
	private Map<Integer, Mmenu> map = new HashMap<Integer, Mmenu>();
	private List<Mmenu> listMmenu = new ArrayList<Mmenu>();
	private List<Musergroupmenu> listGroup = new ArrayList<Musergroupmenu>();
	private List<Mmenu> objList = new ArrayList<>();
	private boolean isInsert;
	private String isDetail;

	@Wire
	private Label lbTitle;

	@Wire
	private Grid gridMenu;

	@Wire
	private Window winUsergroupform;

	@Wire
	private Button btnSave, btnCancel;

	@Wire
	private Groupbox gbSetup, gbDaftar;

	@Wire
	private Div divRoot, divFooter, divListMenu;

	@Wire
	private Textbox txUsergroupcode, txUsergroupname, txUsergroupdesc;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("isEdit") final String isEdit, @ExecutionArgParam("isDetail") final String isDetail,
			@ExecutionArgParam("obj") Musergroup obj) throws Exception {
		Selectors.wireComponents(view, this, false);

		oUser = (Muser) zkSession.getAttribute("oUser");
		doReset();
		lbTitle.setValue("Form Grup User");
		gbDaftar.setVisible(false);
		gbSetup.setVisible(true);
		if (obj != null) {
			this.obj = obj;
			System.out.println(obj.getUsergroupcode());
			isInsert = false;
		}

		if (isEdit != null && isEdit.equals("Y")) {
			btnSave.setLabel(Labels.getLabel("common.update"));
			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_musergroupmenu()
					+ "/usergroup/" + obj.getId();
			System.out.println(url);
			ObjectResp rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
			if (rsp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				listGroupmenu = mapper.convertValue(rsp.getData(), new TypeReference<List<Musergroupmenu>>() {
				});

				for (Musergroupmenu data : listGroupmenu) {
					map.put(data.getMenu().getMmenupk(), data.getMenu());
				}

				System.out.println("mapsizeee : " + map.size());
				if (map.size() > 0) {
					btnSave.setDisabled(false);
				}
			}
		}

		if (isDetail != null && isDetail.equals("Y")) {
			this.isDetail = isDetail;
			txUsergroupcode.setReadonly(true);
			txUsergroupdesc.setReadonly(true);
			txUsergroupname.setReadonly(true);
			lbTitle.setValue("Detail Data Role");
//			readOnly();
			divFooter.setVisible(false);
			gbDaftar.setVisible(true);
			gbSetup.setVisible(false);

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_musergroupmenu()
					+ "/usergroup/" + obj.getId();
			System.out.println(url);
			ObjectResp rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
			if (rsp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				listGroupmenu = mapper.convertValue(rsp.getData(), new TypeReference<List<Musergroupmenu>>() {
				});

				gridMenu.setModel(new ListModelList<>(listGroupmenu));
			}
		}

		if (gridMenu != null) {
			gridMenu.setRowRenderer(new RowRenderer<Musergroupmenu>() {

				@Override
				public void render(Row row, final Musergroupmenu data, int index) throws Exception {
					row.getChildren().add(new Label(String.valueOf(index + 1)));
					row.getChildren()
							.add(new Label(data.getMenu().getMenugroup() + " - " + data.getMenu().getMenuname()));
				}
			});
		}

		System.out.println(isDetail + " : isdetail");

		doListMenu();

	}

	public void readOnly() {
		txUsergroupcode.setReadonly(true);
		txUsergroupname.setReadonly(true);
		txUsergroupdesc.setReadonly(true);
	}

	@NotifyChange("*")
	public void doListMenu() {
		try {
			divListMenu.getChildren().clear();
			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_mmenu();
			System.out.println(url);
			ObjectResp rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
			if (rsp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(rsp.getData(), new TypeReference<List<Mmenu>>() {
				});
				System.out.println(objList.size());
				String menugroup = "";
				Grid grid = null;
				Rows rows = null;
				for (Mmenu menu : objList) {
					if (!menugroup.equals(menu.getMenugroup().trim())) {
						menugroup = menu.getMenugroup().trim();
						divListMenu.appendChild(new Separator());
						grid = new Grid();
						Columns columns = new Columns();
						Column column1 = new Column();
						column1.setAlign("center");
						column1.setWidth("60px");
						final Checkbox chk = new Checkbox();

						chk.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

							@Override
							public void onEvent(Event event) throws Exception {
								doCheckedall((Grid) chk.getParent().getParent().getParent(), chk.isChecked());
							}
						});

						column1.appendChild(chk);
						Column column2 = new Column(menugroup);
						column2.setStyle(
								"color:black; font-size:14px; font-weight:bold;background-color:#f2f2f2;border-color:#f2f2f2");
						columns.setStyle("background-color:#f2f2f2; border-color:#f2f2f2");
						column1.setStyle("background-color:#f2f2f2;border-color:#f2f2f2");
						columns.appendChild(column1);
						columns.appendChild(column2);
						grid.appendChild(columns);
						rows = new Rows();

						grid.appendChild(rows);
						grid.setParent(divListMenu);
					}
					Row row = new Row();
					final Checkbox chk = new Checkbox();
					chk.setAttribute("obj", menu);
					chk.addEventListener(Events.ON_CHECK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (chk.isChecked()) {
								listMmenu.add((Mmenu) chk.getAttribute("obj"));
								btnSave.setDisabled(false);
							} else {
								listMmenu.remove((Mmenu) chk.getAttribute("obj"));
							}

							if (listMmenu.size() == 0) {
								btnSave.setDisabled(true);
							}
						}
					});
					if (map != null && map.get(menu.getMmenupk()) != null) {
						chk.setChecked(true);
						listMmenu.add(menu);
					} else {
						chk.setChecked(false);
						System.out.println(menu.getMenuname());
					}

					Label label = new Label(menu.getMenuname().trim());
					row.appendChild(chk);
					row.appendChild(label);
					row.setParent(rows);

				}

			}
		} catch (Exception e) {
			if (e.getCause() instanceof ConnectException) {
				Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.ERROR);
			}
			e.printStackTrace();
		}
	}

	public void doCheckedall(Grid grid, boolean checked) {
		try {
			List<Row> components = grid.getRows().getChildren();
			for (Row comp : components) {
				Checkbox chk = (Checkbox) comp.getChildren().get(0);
				if (checked) {
					chk.setChecked(true);
					listMmenu.remove(chk.getAttribute("obj"));
					listMmenu.add((Mmenu) chk.getAttribute("obj"));
					btnSave.setDisabled(false);
				} else {
					chk.setChecked(false);
					listMmenu.remove(chk.getAttribute("obj"));
				}

				if (listMmenu.size() == 0) {
					btnSave.setDisabled(true);
				}
			}
		} catch (Exception e) {
			if (e.getCause() instanceof ConnectException) {
				Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.ERROR);
			}
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Command
	@NotifyChange("*")
	public void doSave() {

		Messagebox.show(
				isInsert == true ? Labels.getLabel("common.add.confirm") : Labels.getLabel("common.update.confirm"),
				"Confirm Dialog", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener() {

					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							Muser oUser = (Muser) zkSession.getAttribute("oUser");
							try {
								if (listMmenu.size() > 0) {
									String url = "";
									ObjectMapper mapper = new ObjectMapper();
									if (isInsert) {
										ObjectResp rsp = new ObjectResp();
										obj.setUpdatedby(oUser.getUserid());

										url = ConfigUtil.getConfig().getUrl_base()
												+ ConfigUtil.getConfig().getEndpoint_musergroup();
										System.out.println("save : " + url);
										rsp = RespHandler.responObj(url, mapper.writeValueAsString(obj),
												AppUtil.METHOD_POST, oUser);
										if (rsp.getCode() == 201) {
											url = ConfigUtil.getConfig().getUrl_base()
													+ ConfigUtil.getConfig().getEndpoint_musergroupmenu();
											System.out.println(url);

											obj = mapper.convertValue(rsp.getData(), new TypeReference<Musergroup>() {
											});

											for (Mmenu data : listMmenu) {
												Musergroupmenu objMenu = new Musergroupmenu();
												objMenu.setMenu(data);
												objMenu.setUsergroup(obj);
												listGroup.add(objMenu);
											}

											rsp = RespHandler.responObj(url, mapper.writeValueAsString(listGroup),
													AppUtil.METHOD_POST, oUser);
											if (rsp.getCode() == 201) {
												Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
														+ "  title: 'Informasi',\r\n" + "  text: '"
														+ Labels.getLabel("common.add.success") + "'," + "})");
											} else {
												Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
														+ "  title: 'Informasi',\r\n" + "  text: 'Data gagal disimpan',"
														+ "})");
											}
										} else {
											if (rsp.getCode() != 201
													&& (rsp.getMessage().contains("ConstraintViolationException")
															|| rsp.getMessage().contains("duplicate"))) {
												Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
														+ "  title: 'Informasi',\r\n"
														+ "  text: 'Data gagal disimpan, kode role sudah terdaftar',"
														+ "})");
											}
										}

									} else {
										obj.setUpdatedby(oUser.getUserid());
										obj.setLastupdated(null);
										obj.setCreatetime(null);

										url = ConfigUtil.getConfig().getUrl_base()
												+ ConfigUtil.getConfig().getEndpoint_musergroup();
										System.out.println("update : " + url);

										ObjectResp respobj = new ObjectResp();
										respobj = RespHandler.responObj(url, mapper.writeValueAsString(obj),
												AppUtil.METHOD_PUT, oUser);

										if (respobj.getCode() == 200) {
											url = ConfigUtil.getConfig().getUrl_base()
													+ ConfigUtil.getConfig().getEndpoint_musergroupmenu()
													+ "/usergroup/" + obj.getId();

											respobj = RespHandler.responObj(url, null, AppUtil.METHOD_DEL, oUser);

											if (respobj.getCode() == 200) {
												url = ConfigUtil.getConfig().getUrl_base()
														+ ConfigUtil.getConfig().getEndpoint_musergroupmenu();
												System.out.println(url);

												for (Mmenu data : listMmenu) {
													Musergroupmenu objMenu = new Musergroupmenu();
													objMenu.setMenu(data);
													objMenu.setUsergroup(obj);
													listGroup.add(objMenu);
												}

												ObjectResp rsps = RespHandler.responObj(url,
														mapper.writeValueAsString(listGroup), AppUtil.METHOD_POST,
														oUser);

												if (rsps.getCode() == 201) {
													Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
															+ "  title: 'Informasi',\r\n" + "  text: '"
															+ Labels.getLabel("common.update.success") + "'," + "})");
												}
											}
										}
									}
									doReset();
									doClose();
								} else {
									Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
											+ "  title: 'Informasi',\r\n" + "  text: 'Menu harus dipilih'," + "})");
								}

							} catch (Exception e) {
								if (isInsert)
									Messagebox.show("Error : " + e.getMessage(), WebApps.getCurrent().getAppName(),
											Messagebox.OK, Messagebox.ERROR);
								if (e.getCause() instanceof ConnectException) {
									Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK,
											Messagebox.ERROR);
								}
								e.printStackTrace();
							}
						}
					}
				});

	}

	@Command
	@NotifyChange("*")
	public void doReset() {
		obj = new Musergroup();
		isInsert = true;
		btnSave.setDisabled(true);
		System.out.println("mapsize : " + map.size());
		map.clear();
		doListMenu();
	}

	public void doClose() {
		Event closeEvent = new Event("onClose", winUsergroupform, obj);
		Events.postEvent(closeEvent);
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {
				String usergroupcode = (String) ctx.getProperties("usergroupcode")[0].getValue();
				String usergroupname = (String) ctx.getProperties("usergroupname")[0].getValue();

				if (usergroupcode == null || "".equals(usergroupcode.trim()))
					this.addInvalidMessage(ctx, "usergroupcode", Labels.getLabel("common.validator.empty"));
				if (usergroupname == null || "".equals(usergroupname.trim()))
					this.addInvalidMessage(ctx, "usergroupname", Labels.getLabel("common.validator.empty"));
			}
		};
	}

	public Muser getoUser() {
		return oUser;
	}

	public void setoUser(Muser oUser) {
		this.oUser = oUser;
	}

	public Musergroup getObj() {
		return obj;
	}

	public void setObj(Musergroup obj) {
		this.obj = obj;
	}

	public String getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}

}
