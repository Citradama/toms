package com.sds.toms.viewmodel;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Separator;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.handler.RespHandler;
import com.sds.toms.model.Muniversity;
import com.sds.toms.model.Muser;
import com.sds.toms.model.Tproduct;
import com.sds.toms.pojo.ObjectResp;
import com.sds.toms.util.AppUtil;
import com.sds.utils.config.ConfigUtil;

public class ProductListVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private Integer totalrecord;
	private Muser oUser;
	private List<Tproduct> objList = new ArrayList<Tproduct>();

	@Wire
	private Div divCard;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		oUser = (Muser) zkSession.getAttribute("oUser");
		doReset();
		generateCard();

	}

	public void generateCard() {

		Div divRow = new Div();
		divRow.setClass("row");

		for (Tproduct product : objList) {

			Div divColCard = new Div();
			divColCard.setClass("col-3");
			divColCard.setStyle("border-style: ridge; border-radius:8px; margin: 2px; ");

			divColCard.appendChild(new Separator());

			// image product
			Image img = new Image();
			img.setSrc("/images/lessons/math.jpg");
			img.setWidth("100%");

			Div divBody = new Div();
			divBody.setStyle("border-radius:8px; background-color:#fcfcfc; margin: 5px;");

			// Kategori
			Div rowCat = new Div();
			rowCat.setClass("row-12");
			Label lbCat = new Label("Kategori :");
			lbCat.setStyle("font-weight:bold; font-size: 12px; background-color:#e8e1e9; border-radius:3px");
		
			rowCat.appendChild(lbCat);

			Div rowCat1 = new Div();
			rowCat1.setClass("row-12");
			rowCat1.setAlign("center");
			Label lbCat21 = new Label(product.getCategory().getCategory());
			lbCat21.setStyle("font-weight:bold; font-size: 12px");
			rowCat1.appendChild(lbCat21);

			// Line
			Div rowHr = new Div();
			rowHr.setClass("row-12");
			rowHr.setWidth("100%");
			rowHr.setHeight("2px");
			rowHr.setAlign("center");
			rowHr.setStyle("border-radius:8px; background-color:#795182;");

			// Nama Produk
			Div row = new Div();
			row.setClass("row");
			Div col1 = new Div();
			col1.setClass("col-4");
			Label lbProd = new Label("Produk");
			lbProd.setStyle("font-size: 12px");
			col1.appendChild(lbProd);
			Div col2 = new Div();
			col2.setClass("col-8");
			Label lbProd1 = new Label(" : " + product.getProductname());
			lbProd1.setStyle("font-size: 12px");
			col2.appendChild(lbProd1);
			row.appendChild(col1);
			row.appendChild(col2);

			// Price
			Div rowPrc = new Div();
			rowPrc.setClass("row");
			Div colPrc1 = new Div();
			colPrc1.setClass("col-4");
			Label lbPrc1 = new Label("Harga");
			lbPrc1.setStyle("font-size: 12px");
			colPrc1.appendChild(lbPrc1);
			Div colPrc2 = new Div();
			colPrc2.setClass("col-8");
			Label lbPrc2 = new Label(" : Rp. " + product.getPrice());
			lbPrc2.setStyle("font-size: 12px");
			colPrc2.appendChild(lbPrc2);
			rowPrc.appendChild(colPrc1);
			rowPrc.appendChild(colPrc2);

			Div divButton = new Div();
			divButton.setAlign("center");
			Button btn = new Button();
			btn.setAutodisable("self");
			btn.setStyle("background-color:#795182 !important; color:white; border-radius:10px");
			btn.setLabel("Detail");
			btn.setClass("btn btn-info btn-sm btn-block my-4");
			btn.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

				@Override
				public void onEvent(Event event) throws Exception {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("objForm", product);
					map.put("isEdit", "Y");
					Window win = (Window) Executions.createComponents("/view/product/productform.zul", null, map);
					win.setWidth("60%");
					win.setClosable(true);
					win.doModal();
					win.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

						@Override

						public void onEvent(Event event) throws Exception {
							doReset();
							BindUtils.postNotifyChange(null, null, ProductListVm.this, "*");
						}
					});
				}

			});

			divButton.appendChild(btn);

			divBody.appendChild(rowCat);
			divBody.appendChild(rowCat1);
			divBody.appendChild(new Separator());
			divBody.appendChild(rowHr);
			divBody.appendChild(new Separator());
			divBody.appendChild(row);
			divBody.appendChild(rowPrc);

			divColCard.appendChild(img);
			divColCard.appendChild(new Separator());
			divColCard.appendChild(divBody);
			divColCard.appendChild(divButton);

			divRow.appendChild(divColCard);

		}

		divCard.appendChild(divRow);

	}

	public void doReset() {
		doRefreshModel();
	}

	public void doRefreshModel() {
		String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_tproduct();
		try {
			ObjectResp resp = RespHandler.responObj(url, null, AppUtil.METHOD_GET, oUser);

			if (resp.getCode() == 200) {
				ObjectMapper mapper = new ObjectMapper();
				objList = mapper.convertValue(resp.getData(), new TypeReference<List<Tproduct>>() {
				});

				System.out.println(objList.size());
				totalrecord = objList.size();
			} else {
				System.out.println("nulll");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(Integer totalrecord) {
		this.totalrecord = totalrecord;
	}
}
