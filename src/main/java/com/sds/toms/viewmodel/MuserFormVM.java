package com.sds.toms.viewmodel;

import java.util.Date;
import java.util.List;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.sds.toms.model.Muser;
import com.sds.toms.model.Musergroup;
import com.sds.utils.StringUtils;

public class MuserFormVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser muser = new Muser();
	private Muser oUser;
	private Muser objForm;
	private boolean isInsert;

	@Wire
	private Window winUser;
	@Wire
	private Combobox cbUsergroup;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Muser obj) {
		try {
			Selectors.wireComponents(view, this, false);
			oUser = (Muser) zkSession.getAttribute("oUser");
			if (obj != null) {
				objForm = obj;
				isInsert = false;
				cbUsergroup.setValue(obj.getMusergroup().getUsergroupname());
			} else {
				objForm = new Muser();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doSave() {
		try {
//			List<Muser> muser_ = new MuserDao().filterBy("USERID = '" + muser.getUserid() + "'");
//
//			if (muser_.size() > 0) {
//				Clients.evalJavaScript("" + "Swal.fire(\r\n" + "  'Gagal',\r\n" + "  'Userid telah terdaftar',\r\n"
//						+ "  'error'\r\n" + ")" + "");
//				return;
//			}

			if (isInsert) {
				objForm.setCreatedby(oUser.getUserid());
				objForm.setPassword("");
			} else {
				objForm.setLastupdated(new Date());
				objForm.setUpdatedby(oUser.getUserid());
			}


			if (isInsert) {
				Clients.showNotification(Labels.getLabel("common.add.success"), "info", null, "middle_center", 3000);
			} else {
				Clients.showNotification(Labels.getLabel("common.update.success"), "info", null, "middle_center", 3000);
			}

			doClose();
		} catch (Exception e) {
			e.printStackTrace();
			Messagebox.show(e.getMessage(), WebApps.getCurrent().getAppName(), Messagebox.OK, Messagebox.ERROR);
		} 
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
				String email = (String) ctx.getProperties("email")[0].getValue();
				Musergroup musergroup = (Musergroup) ctx.getProperties("musergroup")[0].getValue();

				if (userid == null || userid.isEmpty()) {
					this.addInvalidMessage(ctx, "userid", Labels.getLabel("common.validator.empty"));
				}
				if (username == null || username.isEmpty()) {
					this.addInvalidMessage(ctx, "username", Labels.getLabel("common.validator.empty"));
				}
				if (password == null || password.isEmpty()) {
					this.addInvalidMessage(ctx, "password", Labels.getLabel("common.validator.empty"));
				}
				if (email == null || email.isEmpty()) {
					this.addInvalidMessage(ctx, "email", Labels.getLabel("common.validator.empty"));
				} else if (!StringUtils.emailValidator(email)) {
					this.addInvalidMessage(ctx, "email", "Invalid e-mail format");
				}
				if (musergroup == null) {
					this.addInvalidMessage(ctx, "musergroup", Labels.getLabel("common.validator.empty"));
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
