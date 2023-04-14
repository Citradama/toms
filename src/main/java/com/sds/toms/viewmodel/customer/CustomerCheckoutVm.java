package com.sds.toms.viewmodel.customer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
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

		this.obj = obj;
		this.divContent = divContent;
		harga = DecimalFormat.getInstance().format(obj.getPrice());
		paymethod = "Pilih Metode Pembayaran";
		
		doRenderWishlist();
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
				objCheckout.setCustid(oUser.getUserid());
				objCheckout.setCustname(oUser.getUsername());
				objCheckout.setPrice(obj.getPrice());
				objCheckout.setProductdesc(obj.getProductdesc());
				objCheckout.setProductid(obj.getProductid());
				objCheckout.setProductimglink(obj.getProductimglink());
				objCheckout.setProductname(obj.getProductname());

				url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tcheckout();
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
	
	@SuppressWarnings("deprecation")
	public void doRenderWishlist() {
		try {
			ObjectResp Resp = null;
			List<Twishlist> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_twishlist();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Twishlist>>() {
				});

				if (objList == null)
					objList = new ArrayList<>();

				for (Twishlist obj : objList) {
					Div divBorder = new Div();
					divBorder.setClass("col-2");
					divBorder.setStyle("border-style: ridge; border-radius:8px; margin: 5px;");

					Separator separator = new Separator();
					divBorder.appendChild(separator);

					Image image = new Image();
					image.setSrc("/images/lessons/math.jpg");
					image.setWidth("100%");
					image.setClass("img-fluid no-filter");
					divBorder.appendChild(image);

					separator = new Separator();
					divBorder.appendChild(separator);
					separator = new Separator();
					divBorder.appendChild(separator);

					Div divRow = new Div();
					divRow.setClass("row");

					Div divCol = new Div();
					divCol.setStyle("background-color:#AADAF2; border-radius:8px;");
					divCol.setClass("col-6");

					Label kategori = new Label(obj.getProduct().getCategoryname());
					kategori.setStyle("font-size:65%; font-family:arial");
					divCol.appendChild(kategori);
					divRow.appendChild(divCol);

					separator = new Separator();
					divBorder.appendChild(separator);
					separator = new Separator();
					divBorder.appendChild(separator);

					Label produk = new Label(obj.getProduct().getProductname());
					divBorder.appendChild(produk);

					separator = new Separator();
					divBorder.appendChild(separator);

					divRow = new Div();
					divRow.setClass("row");

					divCol = new Div();
					divCol.setClass("col-6");

					Label label = new Label("Rp.");
					label.setStyle("font-size:14px; font-family:arial;font-weight:bold");
					divCol.appendChild(label);
					divRow.appendChild(divCol);

					Div divFee = new Div();
					divFee.setAlign("right");
					divFee.setClass("col-6");

					label = new Label(DecimalFormat.getInstance().format(obj.getProduct().getPrice()) + ", 00");
					label.setStyle("font-size:14px; font-family:arial;font-weight:bold");
					divFee.appendChild(label);
					divRow.appendChild(divFee);
					divBorder.appendChild(divRow);

					separator = new Separator();
					divBorder.appendChild(separator);

					divRow = new Div();
					divRow.setClass("row");

					divCol = new Div();
					divCol.setClass("col-6");

					label = new Label("Rating");
					label.setStyle("font-size:14px; font-family:arial;");
					divCol.appendChild(label);
					divRow.appendChild(divCol);

					Div divRating = new Div();
					divRating.setAlign("right");
					divRating.setClass("col-6");

					Hlayout hlayout = new Hlayout();
					label = new Label(obj.getProduct().getPassingscore() != null ? NumberFormat.getInstance().format(obj.getProduct().getPassingscore()) : "0");
					label.setStyle("font-size:14px; font-family:arial;");
					hlayout.appendChild(label);

					Image rateImg = new Image();
					rateImg.setSrc("/images/star.png");
					rateImg.setWidth("20px");
					rateImg.setClass("img-fluid no-filter");
					hlayout.appendChild(rateImg);
					divRating.appendChild(hlayout);
					divRow.appendChild(divRating);
					divBorder.appendChild(divRow);

					separator = new Separator();
					divBorder.appendChild(separator);
					separator = new Separator();
					divBorder.appendChild(separator);

					Div divBtn = new Div();
					divBtn.setAlign("center");

					Button btn = new Button();
					btn.setAutodisable("self");
					btn.setStyle("background-color: #0069ab !important; color:white; border-radius:10px");
					btn.setClass("btn btn-info btn-block my-4");
					btn.setLabel("Beli Sekarang");
					btn.setWidth("90%");
					btn.setTooltiptext("Beli Sekarang");
					btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							divContent.getChildren().clear();
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("content", divContent);
							map.put("obj", obj);
							Executions.createComponents("/view/customer/productdetailcustomer.zul", divContent, map);
						}

					});
					divBtn.appendChild(btn);
					divBorder.appendChild(divBtn);

					separator = new Separator();
					divBorder.appendChild(separator);
					divBorder.setParent(divWishlist);
				}
			}

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
