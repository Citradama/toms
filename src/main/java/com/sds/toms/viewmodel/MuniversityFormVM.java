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
import com.sds.toms.model.Mcust;
import com.sds.toms.model.Muniversity;
import com.sds.toms.model.Muser;
import com.sds.toms.pojo.CustReq;
import com.sds.toms.pojo.ObjectResp;
import com.sds.utils.config.ConfigUtil;

public class MuniversityFormVM {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private Muniversity objForm;
	private boolean isInsert;

	@Wire
	private Window winLecturer;
	@Wire
	private Div divFooter, divPassword;
	@Wire
	private Combobox cbUniv;
	@Wire
	private Input txUnivname;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("objForm") Muniversity objForm, @ExecutionArgParam("isEdit") String isEdit,
			@ExecutionArgParam("isDetail") String isDetail) {
		Selectors.wireComponents(view, this, false);
		try {
			oUser = (Muser) zkSession.getAttribute("oUser");
			doReset();
			
			if (objForm != null) {
				this.objForm = objForm;
				isInsert = false;
			}

			if (isEdit != null && isEdit.equals("Y")) {

			}

			if (isDetail != null && isDetail.equals("Y")) {
				divFooter.setVisible(false);
				txUnivname.setReadonly(true);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doReset() {
		objForm = new Muniversity();
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
							Muser oUser = (Muser) zkSession.getAttribute("oUser");
							try {
								String url = "";
								if (isInsert) {
									ObjectResp rsp = new ObjectResp();
									objForm.setCreatedby(oUser.getUserid());

									url = ConfigUtil.getConfig().getUrl_base()
											+ ConfigUtil.getConfig().getEndpoint_muniversity();
									System.out.println("save : " + url);
									rsp = RespHandler.postObject(url, objForm);
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
											+ ConfigUtil.getConfig().getEndpoint_muniversity();
									System.out.println("update : " + url);

									ObjectResp respobj = new ObjectResp();
									respobj = RespHandler.putObject(url, objForm);

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

				String universityname = (String) ctx.getProperties("universityname")[0].getValue();

				if (universityname == null || universityname.isEmpty()) {
					this.addInvalidMessage(ctx, "universityname", Labels.getLabel("common.validator.empty"));
				}

			}
		};
	}

	public Muniversity getObjForm() {
		return objForm;
	}

	public void setObjForm(Muniversity objForm) {
		this.objForm = objForm;
	}
}
