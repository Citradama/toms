package com.sds.toms.viewmodel;

import java.net.ConnectException;
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
import org.zkoss.json.JSONObject;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mdosen;
import com.sds.toms.model.Muniversity;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Musergroup;
import com.sds.toms.pojo.DosenReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class MdosenFormVM {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private Mdosen objForm;
	private boolean isInsert;
	private String password;
	private String confpass;
	private String valPass;
	private String valConfpass;

	@Wire
	private Window winLecturer;
	@Wire
	private Div divFooter, divPassword;
	@Wire
	private Combobox cbUniv;
	@Wire
	private Input txDosenid, txNik, txDosenname, txEmail, txPosition, txHp, txTitle, txPassword, txConfpass;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("objForm") Mdosen objForm, @ExecutionArgParam("isEdit") String isEdit,
			@ExecutionArgParam("isDetail") String isDetail) {
		Selectors.wireComponents(view, this, false);
		try {
			oUser = (Muser) zkSession.getAttribute("oUser");
			doReset();
			String url = "";

			ObjectResp rsp = new ObjectResp();

//			-------Get Combobox Musergroup-------
			url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_muniversity();
			rsp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);
			if (rsp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				List<Muniversity> objList = mapper.convertValue(rsp.getData(), new TypeReference<List<Muniversity>>() {
				});

				Comboitem comboitem = null;
				for (Muniversity university : objList) {
					comboitem = new Comboitem();
					comboitem.setLabel(university.getUniversityname());
					comboitem.setValue(university);
					cbUniv.appendChild(comboitem);
				}
			}

			if (objForm != null) {
				this.objForm = objForm;
				cbUniv.setValue(objForm.getUniversity().getUniversityname());
				isInsert = false;
			}

			if (isEdit != null && isEdit.equals("Y")) {

			}

			if (isDetail != null && isDetail.equals("Y")) {
				divFooter.setVisible(false);
				doReadonly();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doReadonly() {
		txDosenid.setReadonly(true);
		txDosenname.setReadonly(true);
		txEmail.setReadonly(true);
		txHp.setReadonly(true);
		txNik.setReadonly(true);
		txPosition.setReadonly(true);
		txTitle.setReadonly(true);
		cbUniv.setReadonly(true);
		cbUniv.setButtonVisible(false);
		divPassword.setVisible(false);
	}

	public void doReset() {
		objForm = new Mdosen();
		isInsert = true;
		password = "";
		confpass = "";
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
								String url = "";
								ObjectMapper mapper = new ObjectMapper();
								if (isInsert) {
									ObjectResp rsp = new ObjectResp();
									objForm.setCreatedby(oUser.getUserid());

									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_mdosen();
									DosenReq req = new DosenReq();
									req.setMdosen(objForm);
									req.setPassword(password);
									req.setUsergroupcode("DSN");

									rsp = RespHandler.responObj(url, mapper.writeValueAsString(req), AppUtil.METHOD_POST, oUser);
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
									objForm.setLastupdated(null);
									objForm.setCreatetime(null);

									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_mdosen();
									System.out.println("update : " + url);

									ObjectResp respobj = new ObjectResp();
									respobj = RespHandler.responObj(url, mapper.writeValueAsString(objForm), AppUtil.METHOD_PUT, oUser);

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
		Event closeEvent = new Event("onClose", winLecturer, null);
		Events.postEvent(closeEvent);
	}

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {

				String dosenid = (String) ctx.getProperties("dosenid")[0].getValue();
				String nik = (String) ctx.getProperties("nik")[0].getValue();
				String dosenname = (String) ctx.getProperties("dosenname")[0].getValue();
				String email = (String) ctx.getProperties("email")[0].getValue();
				String title = (String) ctx.getProperties("title")[0].getValue();
				String hp = (String) ctx.getProperties("hp")[0].getValue();
				String position = (String) ctx.getProperties("position")[0].getValue();
				Muniversity university = (Muniversity) ctx.getProperties("university")[0].getValue();

				if (dosenid == null || dosenid.isEmpty()) {
					this.addInvalidMessage(ctx, "dosenid", Labels.getLabel("common.validator.empty"));
				}
				if (nik == null || nik.isEmpty()) {
					this.addInvalidMessage(ctx, "nik", Labels.getLabel("common.validator.empty"));
				}
				if (dosenname == null || dosenname.isEmpty()) {
					this.addInvalidMessage(ctx, "dosenname", Labels.getLabel("common.validator.empty"));
				}
				if (email == null || email.isEmpty()) {
					this.addInvalidMessage(ctx, "email", Labels.getLabel("common.validator.empty"));
				}
				if (title == null || title.isEmpty()) {
					this.addInvalidMessage(ctx, "title", Labels.getLabel("common.validator.empty"));
				}
				if (hp == null || hp.isEmpty()) {
					this.addInvalidMessage(ctx, "hp", Labels.getLabel("common.validator.empty"));
				}
				if (position == null || position.isEmpty()) {
					this.addInvalidMessage(ctx, "position", Labels.getLabel("common.validator.empty"));
				}

				if (password != null || password.equals("")) {
					valPass = Labels.getLabel("common.validator.empty");
				}

				if (confpass != null || confpass.equals("")) {
					valConfpass = Labels.getLabel("common.validator.empty");
				} else {
					if (!confpass.equals(password)) {
						valConfpass = "Password tidak sesuai";
					}
				}

				if (university == null) {
					this.addInvalidMessage(ctx, "university", Labels.getLabel("common.validator.empty"));
				}

			}
		};
	}

	public Mdosen getObjForm() {
		return objForm;
	}

	public void setObjForm(Mdosen objForm) {
		this.objForm = objForm;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfpass() {
		return confpass;
	}

	public void setConfpass(String confpass) {
		this.confpass = confpass;
	}

	public String getValPass() {
		return valPass;
	}

	public void setValPass(String valPass) {
		this.valPass = valPass;
	}

	public String getValConfpass() {
		return valConfpass;
	}

	public void setValConfpass(String valConfpass) {
		this.valConfpass = valConfpass;
	}

}
