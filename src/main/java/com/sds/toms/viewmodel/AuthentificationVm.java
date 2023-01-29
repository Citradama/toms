package com.sds.toms.viewmodel;

import javax.ws.rs.core.MediaType;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.pojo.LoginResp;
import com.sds.utils.config.ConfigUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AuthentificationVm {

	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private String userid;
	private String password;
	private String lblMessage;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);

	}

	@Command
	@NotifyChange("lblMessage")
	public void doLogin() {
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
				jsonReq.put("token", null);

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
					Executions.sendRedirect("/view/indexbo.zul");
				} else {
					lblMessage = rsp.getMessage();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			lblMessage = "Userid and Password can not be empty";
		}
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

}
