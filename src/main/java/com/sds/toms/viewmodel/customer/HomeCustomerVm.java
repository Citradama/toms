package com.sds.toms.viewmodel.customer;

import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Textbox;

import com.sds.toms.pojo.ObjectResp;

public class HomeCustomerVm {

	private Boolean imgfav1;

	@Wire
	private Image imgheart1;
	@Wire
	private Menubar menuBar;
	@Wire
	private Textbox txSearchheader;
	@Wire
	private A aLogin, aUser;
	@Wire
	private Div divMateri, divLogin, divRegister, cardTerbaru;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

		imgfav1 = false;
	}
	
	@Command
	public void doFav() {
		if (imgheart1 != null && imgfav1 == false) {
			imgfav1 = true;
			imgheart1.setSrc("/images/heartred.png");
		} else {
			imgfav1 = false;
			imgheart1.setSrc("/images/heartblank.png");
		}
	}

	@Command
	public void doLoginRegist() {
		aLogin.setVisible(false);
		menuBar.setVisible(false);
		txSearchheader.setVisible(false);
		divMateri.setVisible(false);
		divLogin.setVisible(true);
	}

	@Command
	public void doLogin() {
		aUser.setVisible(true);
		aLogin.setVisible(false);
		menuBar.setVisible(true);
		txSearchheader.setVisible(true);
		divMateri.setVisible(true);
		divLogin.setVisible(false);
	}

	@Command
	public void doRegist() {
		aLogin.setVisible(false);
		menuBar.setVisible(false);
		txSearchheader.setVisible(false);
		divMateri.setVisible(false);
		divLogin.setVisible(false);
		divRegister.setVisible(true);
	}
	
	public void doRenderTerbaru() {
		try {
			ObjectResp Resp = null;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean getImgfav1() {
		return imgfav1;
	}

	public void setImgfav1(Boolean imgfav1) {
		this.imgfav1 = imgfav1;
	}
}
