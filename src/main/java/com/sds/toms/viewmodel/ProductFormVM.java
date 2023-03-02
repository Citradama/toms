package com.sds.toms.viewmodel;

import java.math.BigDecimal;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.Validator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Input;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

import com.sds.toms.model.Mcategory;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tproduct;
import com.sds.toms.util.AppData;

public class ProductFormVM {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Muser oUser;
	private Tproduct objForm;
	private boolean isInsert;

	@Wire
	private Window winProduct;
	@Wire
	private Div divFooter, divQuest;
	@Wire
	private Combobox cbUsergroup;
	@Wire
	private Grid gridGenerate, gridManual, gridQuest;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("objForm") Tproduct objForm, @ExecutionArgParam("isEdit") String isEdit,
			@ExecutionArgParam("isDetail") String isDetail) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		
		if (objForm != null) {
			this.objForm = objForm;
		}

	}
	
	 @Command
	 public void doReset() {
		 objForm = new Tproduct();
	 }

	public Validator getValidator() {
		return new AbstractValidator() {

			@Override
			public void validate(ValidationContext ctx) {

				String productid = (String) ctx.getProperties("productid")[0].getValue();
				String productname = (String) ctx.getProperties("productname")[0].getValue();
				Integer totalquest = (Integer) ctx.getProperties("totalquest")[0].getValue();
				Integer passingscore = (Integer) ctx.getProperties("passingscore")[0].getValue();
				BigDecimal price = (BigDecimal) ctx.getProperties("price")[0].getValue();
				String productdesc = (String) ctx.getProperties("productdesc")[0].getValue();
				
				if (productid == null || productid.isEmpty()) {
					this.addInvalidMessage(ctx, "productid", Labels.getLabel("common.validator.empty"));
				}
				
				if (productname == null || productname.isEmpty()) {
					this.addInvalidMessage(ctx, "productname", Labels.getLabel("common.validator.empty"));
				}
				
				if (totalquest == null) {
					this.addInvalidMessage(ctx, "totalquest", Labels.getLabel("common.validator.empty"));
				}
				
				if (passingscore == null) {
					this.addInvalidMessage(ctx, "passingscore", Labels.getLabel("common.validator.empty"));
				}
				
				if (price == null) {
					this.addInvalidMessage(ctx, "price", Labels.getLabel("common.validator.empty"));
				}
				
				if (productdesc == null || productdesc.isEmpty()) {
					this.addInvalidMessage(ctx, "productdesc", Labels.getLabel("common.validator.empty"));
				}
			}
		};
	}

	@Command
	public void doGenerate(@BindingParam("arg") String arg) {

		if (arg.equals("Generate")) {
			gridGenerate.setVisible(true);
			gridManual.setVisible(false);
		} else {
			gridGenerate.setVisible(false);
			gridManual.setVisible(true);
		}
	}

	public ListModelList<Mcategory> getMcategorymodel() {
		ListModelList<Mcategory> lm = null;
		try {
			lm = new ListModelList<Mcategory>(AppData.getMcategory());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}

	public Muser getoUser() {
		return oUser;
	}

	public Tproduct getObjForm() {
		return objForm;
	}

	public void setoUser(Muser oUser) {
		this.oUser = oUser;
	}

	public void setObjForm(Tproduct objForm) {
		this.objForm = objForm;
	}
}
