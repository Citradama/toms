package com.sds.utils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sds.toms.domain.Muser;
import com.sds.toms.pojo.ReqLogin;
import com.sds.toms.pojo.RespUser;
import com.sds.utils.config.ConfigUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@WebListener
public class SessionListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("Session Created");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Session Destroyed");
		try {
			Muser oUser = (Muser) arg0.getSession().getAttribute("sessUser");
			if (oUser != null) {
				try {
					String url = ConfigUtil.getConfig().getUrl_base() + ConfigUtil.getConfig().getEndpoint_muser()
							+ "/logout";
					System.out.println("url : " + url);
					Client client = Client.create();
					client.setConnectTimeout(40 * 1000);
					client.setReadTimeout(40 * 1000);

					try {

						ReqLogin req = new ReqLogin();
						req.setUserid(oUser.getUserid());
						req.setPassword(oUser.getPassword());

						WebResource webResource = client.resource(url.trim());

						ObjectMapper mapper = new ObjectMapper();
						ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
								.post(ClientResponse.class, mapper.writeValueAsString(req));

						String output = response.getEntity(String.class);
						System.out.println(output);

						mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
						RespUser rsp = mapper.readValue(output, RespUser.class);
						if (rsp.getMessage().equals("success")) {
							System.out.println("SUCCESS");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}