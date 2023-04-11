package com.sds.toms.viewmodel.customer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
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
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Mcust;
import com.sds.toms.model.Mpaymethod;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tcheckout;
import com.sds.toms.model.Tproduct;
import com.sds.toms.model.Twishlist;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class CustomerCheckoutVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;
	private Mcust oCust;

	private Mpaymethod mpaymethod;

	private Tproduct obj;
	private String harga;
	private String paymethod;

	private Div divContent;

	@Wire
	private Div divWishlist;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("obj") Tproduct obj,
			@ExecutionArgParam("content") Div divContent) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		oCust = (Mcust) zkSession.getAttribute("oCust");

		this.obj = obj;
		this.divContent = divContent;
		harga = DecimalFormat.getInstance().format(obj.getPrice());
		paymethod = "Pilih Metode Pembayaran";
	}

	@Command
	@NotifyChange("*")
	public void doSelectPayment() {
		try {
			Window win = (Window) Executions.createComponents("/view/customer/checkoutpayment.zul", null, null);
			win.setWidth("40%");
			win.setClosable(true);
			win.doModal();
			win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {
				@SuppressWarnings("unchecked")
				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getData() != null) {
						Map<String, Object> map = (Map<String, Object>) event.getData();
						mpaymethod = (Mpaymethod) map.get("paymethod");
						if (mpaymethod != null)
							paymethod = mpaymethod.getPaymethod();

						BindUtils.postNotifyChange(null, null, CustomerCheckoutVm.this, "paymethod");
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doRenderWishlist() {
		try {
			List<Twishlist> objList = new ArrayList<>();
			ObjectResp Resp = null;

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tproduct();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Twishlist>>() {
				});

				if (objList == null)
					objList = new ArrayList<>();

				for (Twishlist obj : objList) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Command
	public void doSave() {
		if (mpaymethod != null) {
			try {
				String url = "";
				ObjectMapper mapper = new ObjectMapper();
				ObjectResp rsp = null;

				Tcheckout objCheckout = new Tcheckout();
				objCheckout.setCategory(obj.getCategoryname());
				objCheckout.setCategoryid(obj.getCategoryid());
				objCheckout.setChecktime(new Date());
				objCheckout.setCustid(oCust.getCustid());
				objCheckout.setCustname(oCust.getCustname());
				objCheckout.setPrice(obj.getPrice());
				objCheckout.setProductdesc(obj.getProductdesc());
				objCheckout.setProductid(obj.getProductid());
				objCheckout.setProductimglink(obj.getProductimglink());
				objCheckout.setProductname(obj.getProductname());

				url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_twishlist();
				rsp = RespHandler.responObj(url, mapper.writeValueAsString(objCheckout), AppUtil.METHOD_POST, oUser);
				if (rsp.getCode() == 201 || rsp.getCode() == 200) {
					Clients.evalJavaScript("swal.fire({" + "icon: 'success',\r\n" + "  title: 'Berhasil',\r\n"
							+ "  text: 'Checkout berhasil.'," + "})");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Clients.evalJavaScript("swal.fire({" + "icon: 'warning',\r\n" + "  title: 'Informasi',\r\n"
					+ "  text: 'Silahkan pilih metode pembayaran.'," + "})");
		}
	}

	public Tproduct getObj() {
		return obj;
	}

	public void setObj(Tproduct obj) {
		this.obj = obj;
	}

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}

	public String getPaymethod() {
		return paymethod;
	}

	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
}
