package com.sds.toms.viewmodel;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mmenu;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Musergroup;
import com.sds.toms.model.Musergroupmenu;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.StringUtils;
import com.sds.utils.config.ConfigUtil;

public class MuserFormVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser muser = new Muser();
	private Muser oUser;
	private Muser objForm;
	private boolean isInsert;

	@Wire
	private Window winUser;
	@Wire
	private Div divFooter;
	@Wire
	private Combobox cbUsergroup;
	@Wire
	private Input txUserid, txUsername, txPassword;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Muser obj,
			@ExecutionArgParam("isEdit") String isEdit, @ExecutionArgParam("isDetail") String isDetail) {
		try {
			Selectors.wireComponents(view, this, false);
			oUser = (Muser) zkSession.getAttribute("oUser");
			doReset();
			String url = "";
			ObjectResp rsp = new ObjectResp();

//			-------Get Combobox Musergroup-------
			url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_musergroup();
			rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (rsp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				List<Musergroup> objList = mapper.convertValue(rsp.getData(), new TypeReference<List<Musergroup>>() {
				});

				Comboitem comboitem = null;
				for (Musergroup musergroup : objList) {
					comboitem = new Comboitem();
					comboitem.setLabel(musergroup.getUsergroupname());
					comboitem.setValue(musergroup);
					cbUsergroup.appendChild(comboitem);
				}
			}

			if (obj != null) {
				objForm = obj;
				isInsert = false;
				cbUsergroup.setValue(obj.getUsergroup().getUsergroupname());
			} else {
				objForm = new Muser();
			}

			if (isDetail != null && isDetail.equals("Y")) {
				cbUsergroup.setButtonVisible(false);
				cbUsergroup.setReadonly(true);
				txPassword.setReadonly(true);
				txUserid.setReadonly(true);
				txUsername.setReadonly(true);
				divFooter.setVisible(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doReset() {
		objForm = new Muser();
		isInsert = true;
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
							try {
								String url = "";
								ObjectMapper mapper = new ObjectMapper();
								if (isInsert) {
									ObjectResp rsp = new ObjectResp();
									objForm.setUpdatedby(oUser.getUserid());

									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_muser();

									rsp = RespHandler.responObj(url, mapper.writeValueAsString(objForm),
											AppUtil.METHOD_POST, oUser);
									if (rsp.getCode() == 201) {

										Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
												+ "  title: 'Berhasil',\r\n" + "  text: '"
												+ Labels.getLabel("common.add.success") + "'," + "})");

									} else {
										if (rsp.getCode() != 201
												&& (rsp.getMessage().contains("ConstraintViolationException")
														|| rsp.getMessage().contains("duplicate"))) {
											Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n"
													+ "  title: 'Informasi',\r\n"
													+ "  text: 'Data gagal disimpan, kode role sudah terdaftar',"
													+ "})");
										} else {
											Clients.evalJavaScript(
													"swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
															+ "  text: 'Data gagal disimpan'," + "})");
										}
									}

								} else {
									objForm.setUpdatedby(oUser.getUserid());
									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_muser();
									ObjectResp respobj = new ObjectResp();
									respobj = RespHandler.responObj(url, mapper.writeValueAsString(objForm),
											AppUtil.METHOD_PUT, oUser);

									if (respobj.getCode() == 200) {
										Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n"
												+ "  title: 'Berhasil',\r\n" + "  text: '"
												+ Labels.getLabel("common.update.success") + "'," + "})");
									}
								}

								doClose();

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

	public void doClose() {
		Event closeEvent = new Event("onClose", winUser, null);
		Events.postEvent(closeEvent);
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {

				String userid = (String) ctx.getProperties("userid")[0].getValue();
				String username = (String) ctx.getProperties("username")[0].getValue();
				String password = (String) ctx.getProperties("password")[0].getValue();
				Musergroup usergroup = (Musergroup) ctx.getProperties("usergroup")[0].getValue();

				if (userid == null || userid.isEmpty()) {
					this.addInvalidMessage(ctx, "userid", Labels.getLabel("common.validator.empty"));
				}
				if (username == null || username.isEmpty()) {
					this.addInvalidMessage(ctx, "username", Labels.getLabel("common.validator.empty"));
				}
				if (password == null || password.isEmpty()) {
					this.addInvalidMessage(ctx, "password", Labels.getLabel("common.validator.empty"));
				}
				if (usergroup == null) {
					this.addInvalidMessage(ctx, "usergroup", Labels.getLabel("common.validator.empty"));
				}

			}
		};
	}

	public Muser getObjForm() {
		return objForm;
	}

	public void setObjForm(Muser objForm) {
		this.objForm = objForm;
	}

}
