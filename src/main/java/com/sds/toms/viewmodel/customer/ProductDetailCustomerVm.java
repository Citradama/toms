package com.sds.toms.viewmodel.customer;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcust;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tproduct;
import com.sds.toms.model.Twishlist;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class ProductDetailCustomerVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	private Mcust oCust;

	private Tproduct obj;
	private String username;
	private String searchcover;
	private String harga;
	private String lblTotalmateri;
	private String lblTotalsoal;

	private Div divContent;

	@Wire
	private Button btnWishlist, btnUnwishlist;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tproduct obj,
			@ExecutionArgParam("content") Div divContent) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		oCust = (Mcust) zkSession.getAttribute("oCust");

		this.obj = obj;
		this.divContent = divContent;
		username = oUser.getUsername();
		searchcover = "";
		harga = DecimalFormat.getInstance().format(obj.getPrice());

		lblTotalmateri = "- Terdiri dari 1 modul materi pembelajaran yang diberikan.";
		lblTotalsoal = "- Terdiri dari paket soal try out dengan jawaban yang diberikan diakhir pengerjaan sebanyak 10 soal";

		if (obj.getIswishlist() != null && obj.getIswishlist().equals("Y")) {
			btnUnwishlist.setVisible(true);
			btnWishlist.setVisible(false);
		} else {
			btnWishlist.setVisible(true);
			btnUnwishlist.setVisible(false);
		}
	}

	@Command
	public void doWishlist() {
		try {
			String url = "";
			ObjectMapper mapper = new ObjectMapper();
			ObjectResp rsp = null;

			Twishlist objWish = new Twishlist();
			objWish.setCustid(oCust.getCustid());
			objWish.setCustname(oCust.getCustname());
			objWish.setProduct(obj);

			url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_twishlist();
			rsp = RespHandler.responObj(url, mapper.writeValueAsString(objWish), AppUtil.METHOD_POST, oUser);
			if (rsp.getCode() == 201 || rsp.getCode() == 200) {
				url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tproduct();
				obj.setIswishlist("Y");

				url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_twishlist();
				rsp = RespHandler.responObj(url, mapper.writeValueAsString(objWish), AppUtil.METHOD_PUT, oUser);

				if (rsp.getCode() == 201 || rsp.getCode() == 200) {
					btnUnwishlist.setVisible(true);
					btnWishlist.setVisible(false);
					Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n" + "  title: 'Berhasil',\r\n"
							+ "  text: 'Paket berhasil disimpan.'," + "})");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doUnwishlist() {
		try {
			String url = "";
			ObjectMapper mapper = new ObjectMapper();
			ObjectResp rsp = null;
			
			url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_twishlist();
			rsp = RespHandler.responObj(url, null, AppUtil.METHOD_DEL, oUser);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Command
	public void doBuy() {
		try {
			divContent.getChildren().clear();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("content", divContent);
			map.put("obj", obj);
			Executions.createComponents("/view/customer/customercheckout.zul", divContent, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Tproduct getObj() {
		return obj;
	}

	public void setObj(Tproduct obj) {
		this.obj = obj;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSearchcover() {
		return searchcover;
	}

	public void setSearchcover(String searchcover) {
		this.searchcover = searchcover;
	}

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}

	public String getLblTotalmateri() {
		return lblTotalmateri;
	}

	public void setLblTotalmateri(String lblTotalmateri) {
		this.lblTotalmateri = lblTotalmateri;
	}

	public String getLblTotalsoal() {
		return lblTotalsoal;
	}

	public void setLblTotalsoal(String lblTotalsoal) {
		this.lblTotalsoal = lblTotalsoal;
	}
}
