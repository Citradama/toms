package com.sds.toms.viewmodel.customer;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Textbox;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.pojo.LoginResp;
import com.sds.utils.config.ConfigUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CustomerCoverVm {
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private Boolean imgfav1;
	private String userid;
	private String password;
	private String lblMessage;
	private String searchcover;
	
	private Div divContent;
	
	@Wire
	private Image imgheart1;
	@Wire
	private Menubar menuBar;
	@Wire
	private Textbox txSearchheader;
	@Wire
	private A aLogin, aUser;
	@Wire
	private Div divMateri, divLogin, divRegister;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view, @ExecutionArgParam("content") Div divContent) {
		Selectors.wireComponents(view, this, false);

		searchcover = "";
		imgfav1 = false;
		this.divContent = divContent;
	}

	@Command
	public void doLogin() {
		try {
			if (userid != null && !userid.trim().equals("") && password != null && !password.trim().equals("")) {
				try {
					String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_muser() + "/login";
					System.out.println("url : " + url);
					Client client = Client.create();
					client.setConnectTimeout(40 * 1000);
					client.setReadTimeout(40 * 1000);

					JSONObject jsonReq = new JSONObject();

					jsonReq.put("password", password.trim());
					jsonReq.put("userid", userid.trim());

					WebResource webResource = client.resource(url.trim());

					ObjectMapper mapper = new ObjectMapper();
					System.out.println("ReqUpdate : " + mapper.writeValueAsString(jsonReq));
					ClientResponse response = webResource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class,
							mapper.writeValueAsString(jsonReq));

					String output = response.getEntity(String.class);
					System.out.println(output);

					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					LoginResp rsp = mapper.readValue(output, LoginResp.class);
					if (rsp.getCode() == 200) {
						zkSession.setAttribute("oUser", rsp.getData());
						divContent.getChildren().clear();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("content", divContent);
						Executions.createComponents("/view/homecustomer.zul", divContent, map);
					} else {
						lblMessage = rsp.getMessage();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				lblMessage = "Userid and Password can not be empty";
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	public void doRegist() {
		aLogin.setVisible(false);
		menuBar.setVisible(false);
		txSearchheader.setVisible(false);
		divMateri.setVisible(false);
		divLogin.setVisible(false);
		divRegister.setVisible(true);
	}

	public Boolean getImgfav1() {
		return imgfav1;
	}

	public void setImgfav1(Boolean imgfav1) {
		this.imgfav1 = imgfav1;
	}

	public String getLblMessage() {
		return lblMessage;
	}

	public void setLblMessage(String lblMessage) {
		this.lblMessage = lblMessage;
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

	public String getSearchcover() {
		return searchcover;
	}

	public void setSearchcover(String searchcover) {
		this.searchcover = searchcover;
	}
}
