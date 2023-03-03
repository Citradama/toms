package com.sds.toms.viewmodel.customer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Textbox;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tproduct;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class HomeCustomerVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Muser oUser;

	private Boolean imgfav1;
	private String userid;
	private String password;
	private String lblMessage;
	private String username;
	private String searchcover;

	private Div divContent;

	@Wire
	private Menubar menuBar;
	@Wire
	private Textbox txSearchheader;
	@Wire
	private Div divMateri, divLogin, divRegister, cardTerbaru;

	@AfterCompose
	@NotifyChange("*")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view,
			@ExecutionArgParam("content") Div divContent) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");

		if (oUser != null) {
			imgfav1 = false;
			username = "Hi, " + oUser.getUsername();
			this.divContent = divContent;
			searchcover = "";
			doRenderTerbaru();
		}
	}

	@SuppressWarnings("deprecation")
	public void doRenderTerbaru() {
		try {
			ObjectResp Resp = null;
			List<Tproduct> objList = new ArrayList<>();

			String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tproduct();
			Resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (Resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(Resp.getData(), new TypeReference<List<Tproduct>>() {
				});

				if (objList == null)
					objList = new ArrayList<>();

				for (Tproduct obj : objList) {
					Div divBorder = new Div();
					divBorder.setClass("col-3");
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

					Label kategori = new Label(obj.getCategory().getCategory());
					kategori.setStyle("font-size:65%; font-family:arial");
					divCol.appendChild(kategori);
					divRow.appendChild(divCol);

					Div divFav = new Div();
					divFav.setAlign("right");
					divFav.setClass("col-6");

					A aFav = new A();
					final Image Favimg = new Image();
					Favimg.setWidth("24px");
					Favimg.setClass("img-fluid no-filter");
					Favimg.setSrc("/images/heartblank.png");
					aFav.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							if (imgfav1 == false) {
								imgfav1 = true;
								Favimg.setSrc("/images/heartred.png");
							} else {
								imgfav1 = false;
								Favimg.setSrc("/images/heartblank.png");
							}
						}
					});
					aFav.appendChild(Favimg);
					divFav.appendChild(aFav);
					divRow.appendChild(divFav);
					divBorder.appendChild(divRow);

					separator = new Separator();
					divBorder.appendChild(separator);
					separator = new Separator();
					divBorder.appendChild(separator);

					Label produk = new Label(obj.getProductname());
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

					label = new Label(DecimalFormat.getInstance().format(obj.getPrice()) + ", 00");
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
					label = new Label(NumberFormat.getInstance().format(obj.getPassingscore()));
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
					divBtn.appendChild(btn);
					divBorder.appendChild(divBtn);

					separator = new Separator();
					divBorder.appendChild(separator);
					divBorder.setParent(cardTerbaru);
				}
			}

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

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLblMessage() {
		return lblMessage;
	}

	public void setLblMessage(String lblMessage) {
		this.lblMessage = lblMessage;
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
}
